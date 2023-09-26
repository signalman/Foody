from fastapi import APIRouter
from pydantic import BaseModel
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

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


class IngredientInput(BaseModel):
    ingredients: str


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


@router.post("/ingredients")
async def get_top_recipes(item: IngredientInput, top_k: int = 30):
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(recipe_data_cleaned['ingredients_concat'])
    ingredients_vector = vectorizer.transform([item.ingredients])
    cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()
    top_indices = cosine_similarities.argsort()[-top_k:][::-1]
    # top_recipes = [(recipe_data_cleaned.iloc[index]['recipe_id'], cosine_similarities[index]) for index in top_indices]
    top_recipes = [(int(recipe_data_cleaned.iloc[index]['recipe_id']), cosine_similarities[index]) for index in
                   top_indices]

    return {"Top Recipes": top_recipes}


@router.post("/nutrient")
async def get_nutrient_recommendations(user_deficiency: UserDeficiencyInput, top_k: int = 5):
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


@router.post("/nutrients")
def get_nutrient_recommendations_euclidean(user_deficiency: UserDeficiencyInput, top_k: int = 5):
    user_vector = np.array(list(user_deficiency.dict().values()))
    nutrient_columns = list(user_deficiency.dict().keys())
    recipe_vectors = recipe_data[nutrient_columns].values
    distances = np.array([euclidean_distance(user_vector, recipe_vector) for recipe_vector in recipe_vectors])
    top_indices = distances.argsort()[:top_k]
    top_recipes = [{"recipe_id": int(recipe_data.iloc[index]['recipe_id']), "distance": distances[index]} for index in
                   top_indices]
    return {"Top Nutrient-Based Recipes (Euclidean Distance)": top_recipes}
