from fastapi import APIRouter
from pydantic import BaseModel
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import math

router = APIRouter()

# energy 칼로리
# carbohydrates 탄수화물
# protein 단백질
# fats 지방
# dietaryFiber 식이섬유
# calcium 칼슘
# sodium 나트륨
# iron 아연
# vitaminA 비타민A
# vitaminC 비타민C


# 데이터 로딩
recipe_data = pd.read_csv('data/recipe_information_reprocessed.csv')
recipe_data_cleaned = recipe_data.dropna(subset=['ingredients_concat'])
recipe_data_jaccard = pd.read_csv('data/recipe_jaccard.csv')
recipe_data_jaccard_list = pd.read_csv('data/recipe_jaccard_list.csv')

# 재료 유사성 점수 계산
vectorizer = TfidfVectorizer()
tfidf_matrix = vectorizer.fit_transform(recipe_data_cleaned['ingredients_concat'])


class IngredientInput(BaseModel):
    ingredients: str
    top_k: int


# 부족 영양소
class UserDeficiencyInput(BaseModel):
    energy: float
    carbohydrates: float
    protein: float
    fats: float
    dietaryFiber: float
    calcium: float
    sodium: float
    iron: float
    vitaminA: float
    vitaminC: float


# 재료, 영양소 기반 추천 클래스
class CombinedInput(BaseModel):
    ingredients: str
    user_deficiency: UserDeficiencyInput


# 자카드 유사도
def jaccard_similarity(set1, set2):
    intersection = set1.intersection(set2)
    union = set1.union(set2)
    return len(intersection) / len(union)


# 기존의 코사인, 맨하탄 등등 유사도는 사용자의 재료가 레시피의 재료를 모두 포함하고, 너무 많으면 유사도가 오히려 낮게 나옴
# 이를 보완하기 위해, 이런 방식의 유사도를 계산해봄
def ingredient_similarity(A, B):
    """Calculate the similarity between two ingredient lists."""
    # Convert each ingredient list to a set
    set_A = set(A.split())
    set_B = set(B.split())

    # Compute the size of the intersection
    intersection_size = len(set_A & set_B)

    # Calculate similarity
    similarity = intersection_size / len(set_B)

    return similarity


# 유사한 레시피와 유사도 검색
# @router.post("/ingredients")
# async def get_top_recipes_from_refrigerator(item: IngredientInput, top_k: int = 30):
#     vectorizer = TfidfVectorizer()
#     tfidf_matrix = vectorizer.fit_transform(recipe_data_cleaned['ingredients_concat'])
#     ingredients_vector = vectorizer.transform([item.ingredients])
#     cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()
#     top_indices = cosine_similarities.argsort()[-top_k:][::-1]
#     # top_recipes = [(recipe_data_cleaned.iloc[index]['recipe_id'], cosine_similarities[index]) for index in top_indices]
#     top_recipes = [(int(recipe_data_cleaned.iloc[index]['recipe_id']), cosine_similarities[index]) for index in
#                    top_indices]
#
#     return {"Top Recipes": top_recipes}

# top_k를 IngredientInput에 넣었음. 아래 메소드들도 전부 바꿔줘야 함
@router.post("/ingredients")
async def get_top_recipes_from_refrigerator(item: IngredientInput):
    top_k = item.top_k
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(recipe_data_cleaned['ingredients_concat'])
    ingredients_vector = vectorizer.transform([item.ingredients])
    cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()
    top_indices = cosine_similarities.argsort()[-top_k:][::-1]

    # top_recipes = [
    #     {
    #         "id": int(recipe_data_cleaned.iloc[index]['recipe_id']),
    #         "energy": recipe_data_cleaned.iloc[index]['energy'],
    #         "protein": recipe_data_cleaned.iloc[index]['protein'],
    #         "dietaryFiber": recipe_data_cleaned.iloc[index]['dietaryFiber'],
    #         "calcium": recipe_data_cleaned.iloc[index]['calcium'],
    #         "sodium": recipe_data_cleaned.iloc[index]['sodium'],
    #         "iron": recipe_data_cleaned.iloc[index]['iron'],
    #         "fats": recipe_data_cleaned.iloc[index]['fats'],
    #         "vitaminA": recipe_data_cleaned.iloc[index]['vitaminA'],
    #         "vitaminC": recipe_data_cleaned.iloc[index]['vitaminC']
    #     }
    #     for index in top_indices
    # ]

    top_recipe_ids = [int(recipe_data_cleaned.iloc[index]['recipe_id']) for index in top_indices]

    return top_recipe_ids


@router.post("/ingredients/jaccard")
def get_top_recipes_based_on_jaccard(item: IngredientInput, top_k: int = 5):
    # Convert the ingredients string into a set
    user_ingredients = set(item.ingredients.split())

    # Calculate Jaccard Similarity for each recipe
    jaccard_scores = []
    for index, row in recipe_data_jaccard.iterrows():
        # Convert the string representation of the set back to an actual set
        recipe_ingredients = eval(row['ingredient_set'])
        if len(recipe_ingredients) >= 5:  # Only consider recipes with at least 5 ingredients
            score = jaccard_similarity(user_ingredients, recipe_ingredients)
            jaccard_scores.append((row['recipe_id'], score))

    # Sort the scores in descending order and get top_k recipes
    sorted_scores = sorted(jaccard_scores, key=lambda x: x[1], reverse=True)
    top_recipes = sorted_scores[:top_k]

    return {"Top Recipes Based on Jaccard Similarity": top_recipes}


@router.post("/nutrient")
async def get_nutrient_recommendations_by_filtering(user_deficiency: UserDeficiencyInput, top_k: int = 5):
    # Calculate recommendation score based on user's nutrient deficiency
    def calculate_score(row):
        score = 0
        for nutrient, deficiency in user_deficiency.dict().items():
            score += row[nutrient] * deficiency
        return score

    recipe_data['recommendation_score'] = recipe_data.apply(calculate_score, axis=1)
    sorted_recipes = recipe_data.sort_values(by="recommendation_score", ascending=False)
    top_recipes = sorted_recipes.head(top_k)[['recipe_id', 'recommendation_score']].to_dict(orient='records')
    return {"Top Nutrient-Based Recipes": top_recipes}


def euclidean_distance(vector1, vector2):
    """Compute the Euclidean Distance between two vectors."""
    return np.sqrt(np.sum((vector1 - vector2) ** 2))


@router.post("/nutrients/euclidean")
def get_nutrient_recommendations_euclidean(user_deficiency: UserDeficiencyInput, top_k: int = 5):
    user_vector = np.array(list(user_deficiency.dict().values()))
    nutrient_columns = list(user_deficiency.dict().keys())
    recipe_vectors = recipe_data[nutrient_columns].values
    distances = np.array([euclidean_distance(user_vector, recipe_vector) for recipe_vector in recipe_vectors])
    top_indices = distances.argsort()[:top_k]
    top_recipes = [{"recipe_id": int(recipe_data.iloc[index]['recipe_id']), "distance": distances[index]} for index in
                   top_indices]
    return {"Top Nutrient-Based Recipes (Euclidean Distance)": top_recipes}


# @router.post("/nutrients/cosine")
# def get_nutrient_recommendations_cosine(user_deficiency: UserDeficiencyInput, top_k: int = 5):
#     # Convert user deficiency into an array (reshape to make it 2D for cosine_similarity function)
#     user_vector = np.array(list(user_deficiency.dict().values())).reshape(1, -1)
#
#     # Extract nutrient columns from the data
#     nutrient_columns = list(user_deficiency.dict().keys())
#     recipe_vectors = recipe_data[nutrient_columns].values
#
#     # Calculate cosine similarities for each recipe
#     similarities = cosine_similarity(user_vector, recipe_vectors).flatten()
#
#     # Get the indices of the top_k most similar recipes
#     top_indices = similarities.argsort()[-top_k:][::-1]
#
#     # Extract top recipes and their similarities
#     top_recipes = [{"recipe_id": int(recipe_data.iloc[index]['recipe_id']), "similarity": similarities[index]} for index
#                    in top_indices]
#
#     return {"Top Nutrient-Based Recipes (Cosine Similarity)": top_recipes}


@router.post("/nutrients/cosine")
def get_nutrient_recommendations_cosine(user_deficiency: UserDeficiencyInput, top_k: int = 5):
    # Convert user deficiency into an array (reshape to make it 2D for cosine_similarity function)
    user_vector = np.array(list(user_deficiency.dict().values())).reshape(1, -1)

    # Extract nutrient columns from the data
    nutrient_columns = list(user_deficiency.dict().keys())
    recipe_vectors = recipe_data[nutrient_columns].values

    # Calculate cosine similarities for each recipe
    similarities = cosine_similarity(user_vector, recipe_vectors).flatten()

    # Get the indices of the top_k most similar recipes
    top_indices = similarities.argsort()[-top_k:][::-1]

    # Extract top recipes and their details
    top_recipes = [
        {
            "id": int(recipe_data.iloc[index]['id']),
            "energy": recipe_data.iloc[index]['energy'],
            "protein": recipe_data.iloc[index]['protein'],
            "dietaryFiber": recipe_data.iloc[index]['dietaryFiber'],
            "calcium": recipe_data.iloc[index]['calcium'],
            "sodium": recipe_data.iloc[index]['sodium'],
            "iron": recipe_data.iloc[index]['iron'],
            "fats": recipe_data.iloc[index]['fats'],
            "vitaminA": recipe_data.iloc[index]['vitaminA'],
            "vitaminC": recipe_data.iloc[index]['vitaminC']
        }
        for index in top_indices
    ]

    return top_recipes


def manhattan_distance(vector1, vector2):
    """Compute the Manhattan Distance between two vectors."""
    return np.sum(np.abs(vector1 - vector2))


@router.post("/nutrients/manhattan")
def get_nutrient_recommendations_manhattan(user_deficiency: UserDeficiencyInput, top_k: int = 5):
    # Convert user deficiency into an array
    user_vector = np.array(list(user_deficiency.dict().values()))

    # Extract nutrient columns from the data
    nutrient_columns = list(user_deficiency.dict().keys())
    recipe_vectors = recipe_data[nutrient_columns].values

    # Calculate Manhattan distances for each recipe
    distances = np.array([manhattan_distance(user_vector, recipe_vector) for recipe_vector in recipe_vectors])

    # Get the indices of the top_k closest recipes
    top_indices = distances.argsort()[:top_k]

    # Extract top recipes and their distances
    top_recipes = [{"recipe_id": int(recipe_data.iloc[index]['recipe_id']), "distance": distances[index]} for index in
                   top_indices]

    return {"Top Nutrient-Based Recipes (Manhattan Distance)": top_recipes}


# 사용하면 안댐 !!
@router.post("/nutrients/recommend99")
def get_combined_recommendations(data: CombinedInput, top_k: int = 5):
    # 1. Calculate ingredient similarity scores
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(recipe_data_cleaned['ingredients_concat'])
    ingredients_vector = vectorizer.transform([data.ingredients])
    cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()

    # 2. Calculate nutrient recommendation scores
    def calculate_score(row):
        score = 0
        for nutrient, deficiency in data.user_deficiency.dict().items():
            score += row[nutrient] * deficiency
        return score

    recipe_data['recommendation_score'] = recipe_data.apply(calculate_score, axis=1)

    # 3. Combine the two scores
    combined_scores = cosine_similarities + recipe_data['recommendation_score'].values

    # 4. Get the indices of the top_k recipes with highest combined scores
    top_indices = combined_scores.argsort()[-top_k:][::-1]

    # 5. Extract top recipes and their scores
    top_recipes = [{"recipe_id": int(recipe_data.iloc[index]['recipe_id']), "combined_score": combined_scores[index]}
                   for index in top_indices]

    return {"Top Combined Recipes": top_recipes}


# 냉장고와 유사한 재료 사용 api
@router.post("/nutrients/recommend/test")
def get_combined_recommendations_v3(data: CombinedInput, top_k: int = 10):
    # 재료 유사성 점수 계산
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(recipe_data_cleaned['ingredients_concat'])
    ingredients_vector = vectorizer.transform([data.ingredients])
    cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()
    ingredient_top_indices = cosine_similarities.argsort()[-1000:][::-1]

    # 영양소 추천 점수 계산
    def calculate_score(row):
        score = 0
        for nutrient, deficiency in data.user_deficiency.dict().items():
            nutrient_value = row[nutrient]
            if nutrient_value > deficiency:
                score -= (nutrient_value - deficiency)
            else:
                score += nutrient_value
        return score

    recipe_data['recommendation_score'] = recipe_data.apply(calculate_score, axis=1)
    nutrient_top_indices = recipe_data['recommendation_score'].argsort()[-1000:][::-1]

    # 두 인덱스의 교집합 계산
    combined_indices = list(set(ingredient_top_indices) & set(nutrient_top_indices))
    combined_indices.sort(key=lambda x: recipe_data.iloc[x]['recommendation_score'], reverse=True)

    # 교집합에서 레시피 추출
    top_recipes = [int(recipe_data.iloc[index]['recipe_id']) for index in combined_indices[:top_k]]

    # 교집합의 크기가 top_k보다 작을 경우 추가적인 레시피 추출
    if len(top_recipes) < top_k:
        additional_needed = top_k - len(top_recipes)

        # 재료 유사성 점수와 영양소 점수 상위 목록에서 아직 선택되지 않은 레시피 추출
        additional_ingredient_indices = [idx for idx in ingredient_top_indices if idx not in combined_indices][
                                        :math.ceil(additional_needed / 2)]
        additional_nutrient_indices = [idx for idx in nutrient_top_indices if idx not in combined_indices][
                                      :math.floor(additional_needed / 2)]

        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_ingredient_indices])
        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_nutrient_indices])

    return top_recipes[:top_k]


# 새로운 유사도 계산 방식
@router.post("/nutrients/new")
def get_top_similar_recipes(data: IngredientInput, top_k: int = 5):
    # Filter recipes that have at least 5 ingredients and reset the index
    filtered_recipes = recipe_data_cleaned[
        recipe_data_cleaned['ingredients_concat'].apply(lambda x: len(x.split()) >= 5)
    ].reset_index(drop=True)

    # Calculate similarity scores for all recipes
    similarity_scores = filtered_recipes['ingredients_concat'].apply(
        lambda recipe_ingredients: ingredient_similarity(data.ingredients, recipe_ingredients)
    )

    # Get the indices of the top_k most similar recipes
    top_indices = similarity_scores.argsort()[-top_k:][::-1]

    # Extract top recipes and their scores using the reset indices
    top_recipes = [
        {"recipe_id": int(filtered_recipes.iloc[index]['recipe_id']),
         "similarity_score": similarity_scores.iloc[index]}
        for index in top_indices
    ]

    return {"Top Similar Recipes": top_recipes}


def calculate_scores_vectorized(df, user_deficiency):
    scores = pd.Series(np.zeros(len(df)), index=df.index)
    for nutrient, deficiency in user_deficiency.dict().items():
        nutrient_values = df[nutrient]
        penalty = nutrient_values - deficiency
        scores += np.where(nutrient_values > deficiency, -penalty, nutrient_values)
    return scores


# 냉장고와 유사한 재료 사용 api 개선
@router.post("/nutrients/recommend")
def get_combined_recommendations_v4(data: CombinedInput, top_k: int = 10):
    # # 재료 유사성 점수 계산
    # ingredients_vector = vectorizer.transform([data.ingredients])
    # cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()
    # ingredient_top_indices = cosine_similarities.argsort()[-1000:][::-1]
    #
    # # 영양소 추천 점수 계산
    # recipe_data['recommendation_score'] = calculate_scores_vectorized(recipe_data, data.user_deficiency)
    # nutrient_top_indices = recipe_data['recommendation_score'].argsort()[-1000:][::-1]
    #
    # # 두 인덱스의 교집합 계산
    # combined_indices = list(set(ingredient_top_indices) & set(nutrient_top_indices))
    # combined_indices.sort(key=lambda x: recipe_data.iloc[x]['recommendation_score'], reverse=True)
    #
    # # 교집합에서 레시피 추출
    # top_recipes = [int(recipe_data.iloc[index]['recipe_id']) for index in combined_indices[:top_k]]
    #
    # # 교집합의 크기가 top_k보다 작을 경우 추가적인 레시피 추출
    # if len(top_recipes) < top_k:
    #     additional_needed = top_k - len(top_recipes)
    #     additional_ingredient_indices = [idx for idx in ingredient_top_indices if idx not in combined_indices][
    #                                     :math.ceil(additional_needed / 2)]
    #     additional_nutrient_indices = [idx for idx in nutrient_top_indices if idx not in combined_indices][
    #                                   :math.floor(additional_needed / 2)]
    #
    #     top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_ingredient_indices])
    #     top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_nutrient_indices])
    #
    # return top_recipes[:top_k]

    # 재료 유사성 점수 계산
    ingredients_vector = vectorizer.transform([data.ingredients])
    cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()
    ingredient_top_indices = cosine_similarities.argsort()[-1000:][::-1]

    def calculate_euclidean_distance_scores(df, user_deficiency):
        user_vector = np.array(list(user_deficiency.dict().values()))
        recipe_vectors = df.values
        # Calculate Euclidean distance
        distances = np.linalg.norm(recipe_vectors - user_vector, axis=1)
        # Convert distance to scores (smaller distance should result in higher score)
        scores = -distances
        return scores

    # 영양소 추천 점수 계산 (Using Euclidean distance)
    recipe_data['recommendation_score'] = calculate_euclidean_distance_scores(recipe_data[['energy', 'carbohydrates', 'protein', 'fats', 'dietaryFiber', 'calcium', 'sodium', 'iron', 'vitaminA', 'vitaminC']], data.user_deficiency)
    nutrient_top_indices = recipe_data['recommendation_score'].argsort()[-1000:][::-1]

    # 두 인덱스의 교집합 계산
    combined_indices = list(set(ingredient_top_indices) & set(nutrient_top_indices))
    combined_indices.sort(key=lambda x: recipe_data.iloc[x]['recommendation_score'], reverse=True)

    # 교집합에서 레시피 추출
    top_recipes = [int(recipe_data.iloc[index]['recipe_id']) for index in combined_indices[:top_k]]

    # 교집합의 크기가 top_k보다 작을 경우 추가적인 레시피 추출
    if len(top_recipes) < top_k:
        additional_needed = top_k - len(top_recipes)
        additional_ingredient_indices = [idx for idx in ingredient_top_indices if idx not in combined_indices][:math.ceil(additional_needed / 2)]
        additional_nutrient_indices = [idx for idx in nutrient_top_indices if idx not in combined_indices][:math.floor(additional_needed / 2)]

        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_ingredient_indices])
        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_nutrient_indices])

    return top_recipes[:top_k]


@router.post("/nutrients/recommend/1")
def get_combined_recommendations_v5(data: CombinedInput, top_k: int = 10):
    # Convert the ingredients string into a set
    user_ingredients_set = set(data.ingredients.split())

    # Calculate Jaccard Similarity for each recipe in the dataframe
    jaccard_scores = []
    for index, row in recipe_data_jaccard.iterrows():

        recipe_ingredients = eval(row['ingredient_set'])
        if len(recipe_ingredients) >= 5:  # Only consider recipes with at least 5 ingredients
            score = jaccard_similarity(user_ingredients_set, recipe_ingredients)
            jaccard_scores.append((index, score))

    # Sort based on Jaccard Similarity
    ingredient_top_indices = [index for index, score in sorted(jaccard_scores, key=lambda x: x[1], reverse=True)]

    # 영양소 추천 점수 계산
    recipe_data['recommendation_score'] = calculate_scores_vectorized(recipe_data, data.user_deficiency)
    nutrient_top_indices = recipe_data['recommendation_score'].argsort()[-1000:][::-1]

    # 두 인덱스의 교집합 계산
    combined_indices = list(set(ingredient_top_indices[:1000]) & set(nutrient_top_indices))
    combined_indices.sort(key=lambda x: recipe_data.iloc[x]['recommendation_score'], reverse=True)

    # 교집합에서 레시피 추출
    top_recipes = [int(recipe_data.iloc[index]['recipe_id']) for index in combined_indices[:top_k]]

    # 교집합의 크기가 top_k보다 작을 경우 추가적인 레시피 추출
    if len(top_recipes) < top_k:
        additional_needed = top_k - len(top_recipes)
        additional_ingredient_indices = [idx for idx in ingredient_top_indices if idx not in combined_indices][:math.ceil(additional_needed / 2)]
        additional_nutrient_indices = [idx for idx in nutrient_top_indices if idx not in combined_indices][:math.floor(additional_needed / 2)]

        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_ingredient_indices])
        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_nutrient_indices])

    return top_recipes[:top_k]


@router.post("/nutrients/recommend/2")
def get_combined_recommendations_v6(data: CombinedInput, top_k: int = 10):

    user_ingredients_set = set(data.ingredients.split())

    jaccard_scores = [(index, jaccard_similarity(user_ingredients_set, set(row['ingredient_list'])))
                      for index, row in recipe_data_jaccard_list.iterrows() if len(row['ingredient_list']) >= 5]

    ingredient_top_indices = [index for index, score in sorted(jaccard_scores, key=lambda x: x[1], reverse=True)]

    # 영양소 추천 점수 계산
    recipe_data['recommendation_score'] = calculate_scores_vectorized(recipe_data, data.user_deficiency)
    nutrient_top_indices = recipe_data['recommendation_score'].argsort()[-1000:][::-1]

    # 두 인덱스의 교집합 계산
    combined_indices = list(set(ingredient_top_indices[:1000]) & set(nutrient_top_indices))
    combined_indices.sort(key=lambda x: recipe_data.iloc[x]['recommendation_score'], reverse=True)

    # 교집합에서 레시피 추출
    top_recipes = [int(recipe_data.iloc[index]['recipe_id']) for index in combined_indices[:top_k]]

    # 교집합의 크기가 top_k보다 작을 경우 추가적인 레시피 추출
    if len(top_recipes) < top_k:
        additional_needed = top_k - len(top_recipes)
        additional_ingredient_indices = [idx for idx in ingredient_top_indices if idx not in combined_indices][:math.ceil(additional_needed / 2)]
        additional_nutrient_indices = [idx for idx in nutrient_top_indices if idx not in combined_indices][:math.floor(additional_needed / 2)]

        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_ingredient_indices])
        top_recipes.extend([int(recipe_data.iloc[index]['recipe_id']) for index in additional_nutrient_indices])

    return top_recipes[:top_k]