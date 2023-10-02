from fastapi import APIRouter
from pydantic import BaseModel
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from algorithm.database.database import database
from algorithm.database.model import member, mbti
from sqlalchemy import select
import random

router = APIRouter()

# 데이터 로딩
recipe_data = pd.read_csv('data/recipe_information_reprocessed.csv')
recipe_data_encoded = pd.get_dummies(recipe_data,
                                     columns=['food_preparation_methods', 'food_situations', 'food_ingredients',
                                              'food_types'])


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


class UserPreference(BaseModel):
    # 난이도
    # difficulty: constr(regex="^(초급|중급|아무나)$")

    # 종류별
    koreanMainDish: int
    westernMainDish: int
    sideDish: int
    dessert: int

    # 상황별
    dailyFood: int
    festivalFood: int
    convenienceFood: int
    snackFood: int
    etcFood: int

    # 재료별
    meat: int
    vegetableSeafood: int
    processedFood: int
    healthFood: int
    grain: int

    # 방법별
    lowCook: int
    highCook: int
    waterCook: int
    rawCook: int
    etcCook: int


class CombineUserInput(BaseModel):
    user_deficiency: UserDeficiencyInput
    user_preference: UserPreference


# 범주형 열에 대한 선호도 정보의 순서
preference_columns = [
    "koreanMainDish", "westernMainDish", "sideDish", "dessert",
    "dailyFood", "festivalFood", "convenienceFood", "snackFood", "etcFood",
    "meat", "vegetableSeafood", "processedFood", "healthFood", "grain",
    "lowCook", "highCook", "waterCook", "rawCook", "etcCook"
]

# 범주형 열에 대한 One-Hot Encoding 열의 순서
encoded_columns = [
    "food_types_한식메인디시", "food_types_양식메인디시", "food_types_밑반찬", "food_types_디저트",
    "food_situations_일상", "food_situations_접대", "food_situations_간편식", "food_situations_술", "food_situations_기타",
    "food_ingredients_고기류", "food_ingredients_채소/해물류", "food_ingredients_가공식품류", "food_ingredients_건강류",
    "food_ingredients_주식류",
    "food_preparation_methods_저온조리", "food_preparation_methods_고온조리", "food_preparation_methods_수분조리",
    "food_preparation_methods_날것", "food_preparation_methods_기타"
]


# 취향 기반 추천(콘텐츠 기반)
@router.post("/")
def get_recommendations_based_on_preference(data: UserPreference, top_k: int = 5):
    # 사용자의 선호도 정보를 배열로 변환
    user_vector = np.array([data.dict()[col] for col in preference_columns])

    # 각 레시피에 대한 One-Hot Encoding 벡터 추출
    recipe_vectors = recipe_data_encoded[encoded_columns].values

    # 각 레시피에 대한 가중 평균 점수 계산
    weighted_scores = np.sum(user_vector * recipe_vectors, axis=1)

    # 가중 평균 점수를 기반으로 상위 top_k개의 레시피 인덱스 추출
    top_indices = weighted_scores.argsort()[-top_k * 2:][::-1]  # 두 배의 인덱스를 가져와 무작위 선택의 범위를 확장

    # 동일한 점수를 가진 레시피에 대해 무작위로 선택
    unique_scores = np.unique(weighted_scores[top_indices])
    final_indices = []
    for score in unique_scores:
        indices_with_same_score = np.where(weighted_scores == score)[0]
        indices_to_add = np.random.choice(indices_with_same_score,
                                          min(len(indices_with_same_score), top_k - len(final_indices)), replace=False)
        final_indices.extend(indices_to_add)
        if len(final_indices) >= top_k:
            break

    # 추천된 레시피의 세부 정보 추출
    top_recipes = [
        {
            "recipe_id": int(recipe_data_encoded.iloc[index]['recipe_id']),
            "ingredients": str(recipe_data_encoded.iloc[index]['ingredients_concat']),  # python 형식으로 변경
            "score": int(weighted_scores[index])  # numpy.int32 to int
        }
        for index in final_indices
    ]

    return top_recipes


# 정규화를 사용한 취향 기반 추천(콘텐츠 기반)
@router.post("/hello")
def get_normalized_recommendations_based_on_preference(data: UserPreference, top_k: int = 5):
    # 사용자의 선호도 정보를 배열로 변환
    user_vector = np.array([data.dict()[col] for col in preference_columns])

    # 선호도 정보를 0과 1 사이로 정규화
    user_vector = MinMaxScaler().fit_transform(user_vector.reshape(-1, 1)).flatten()

    # 각 레시피에 대한 One-Hot Encoding 벡터 추출
    recipe_vectors = recipe_data_encoded[encoded_columns].values

    # 각 레시피에 대한 가중 평균 점수 계산
    weighted_scores = np.sum(user_vector * recipe_vectors, axis=1)

    # 가중 평균 점수를 기반으로 상위 top_k개의 레시피 인덱스 추출
    top_indices = weighted_scores.argsort()[-top_k:][::-1]

    # 추천된 레시피의 세부 정보 추출
    top_recipes = [
        {
            "recipe_id": int(recipe_data_encoded.iloc[index]['recipe_id']),
            "ingredients": str(recipe_data_encoded.iloc[index]['ingredients_concat']),
            "score": int(weighted_scores[index])
        }
        for index in top_indices
    ]

    return top_recipes


# 취향 + 영양소 기반 추천
@router.post("/nutrient")
def get_combined_recommendations_without_ingredients(data: CombineUserInput, top_k: int = 4):
    # 영양소 추천 점수 계산 (과다 섭취에 대한 패널티 적용)
    def calculate_score(row):
        score = 0
        for nutrient, deficiency in data.user_deficiency.dict().items():
            nutrient_value = row[nutrient]
            if nutrient_value > deficiency:
                score -= (nutrient_value - deficiency)  # 과다 섭취에 대한 패널티 적용
            else:
                score += nutrient_value
        return score

    # 영양소 기반 점수 계산
    recipe_data_encoded['recommendation_score'] = recipe_data_encoded.apply(calculate_score, axis=1)
    nutrient_top_indices = recipe_data_encoded['recommendation_score'].argsort()[-1000:][::-1]

    # 사용자 선호도 정보를 encoded_columns 순서에 맞게 재배열
    rearranged_preference = [data.user_preference.dict()[col] for col in preference_columns]

    # 선호도 정보 기반으로 점수 계산
    preference_vector = np.array(rearranged_preference)
    preference_scores = cosine_similarity(preference_vector.reshape(1, -1),
                                          recipe_data_encoded[encoded_columns].values)
    recipe_data_encoded['preference_score'] = preference_scores.flatten()

    # 최종 점수는 영양소 추천 점수와 선호도 점수의 합으로 결정
    recipe_data_encoded['final_score'] = recipe_data_encoded['recommendation_score'] + recipe_data_encoded[
        'preference_score']
    final_top_indices = recipe_data_encoded['final_score'].argsort()[-top_k:][::-1]

    # 상위 레시피와 그 점수 추출
    top_recipes = [{"recipe_id": int(recipe_data_encoded.iloc[index]['recipe_id']),
                    "final_score": recipe_data_encoded.iloc[index]['final_score']} for index in final_top_indices]

    # 상위 레시피 추출 (recipe_id만 포함하도록 수정, 서버 통신용)
    top_recipes = [int(recipe_data_encoded.iloc[index]['recipe_id']) for index in final_top_indices]

    return top_recipes


# 협업 필터링 구현부

@router.post("/collaborative")
async def collaborative_filtering_recommendation(user_data: UserPreference, top_k: int = 5):
    # DB에서 모든 사용자의 mbti 정보 가져오기
    query = select([
        member.c.id,
        member.c.weight,
        member.c.gender,
        member.c.age,
        member.c.activity_level,
        member.c.mbti_id,
        mbti.c.korean_main_dish,
        mbti.c.western_main_dish,
        mbti.c.side_dish,
        mbti.c.dessert,
        mbti.c.daily_food,
        mbti.c.festival_food,
        mbti.c.convenience_food,
        mbti.c.snack_food,
        mbti.c.etc_food,
        mbti.c.meat,
        mbti.c.vegetable_seafood,
        mbti.c.processed_food,
        mbti.c.health_food,
        mbti.c.grain,
        mbti.c.low_cook,
        mbti.c.high_cook,
        mbti.c.water_cook,
        mbti.c.raw_cook,
        mbti.c.etc_cook
    ]).select_from(member.join(mbti, member.c.mbti_id == mbti.c.id))
    all_users_data = await database.fetch_all(query)

    # 사용자-아이템 행렬 구성
    user_item_matrix = np.array([list(data)[6:] for data in all_users_data])

    # 사용자 간의 코사인 유사도 계산
    user_similarity = cosine_similarity(user_item_matrix)

    # 해당 사용자와 다른 사용자 간의 유사도를 기반으로 점수 예측
    user_based_prediction = user_similarity.dot(user_item_matrix) / np.array([np.abs(user_similarity).sum(axis=1)]).T

    # 현재 사용자 데이터 추가
    current_user_data = np.array([
        user_data.koreanMainDish,
        user_data.westernMainDish,
        user_data.sideDish,
        user_data.dessert,
        user_data.dailyFood,
        user_data.festivalFood,
        user_data.convenienceFood,
        user_data.snackFood,
        user_data.etcFood,
        user_data.meat,
        user_data.vegetableSeafood,
        user_data.processedFood,
        user_data.healthFood,
        user_data.grain,
        user_data.lowCook,
        user_data.highCook,
        user_data.waterCook,
        user_data.rawCook,
        user_data.etcCook
    ]).reshape(1, -1)

    # 현재 사용자 데이터를 사용자-아이템 행렬에 추가
    user_item_matrix = np.vstack([user_item_matrix, current_user_data])

    # 사용자 간의 코사인 유사도 계산
    user_similarity = cosine_similarity(user_item_matrix)

    # 해당 사용자와 다른 사용자 간의 유사도를 기반으로 점수 예측
    user_based_prediction = user_similarity.dot(user_item_matrix) / np.array([np.abs(user_similarity).sum(axis=1)]).T

    # 현재 사용자의 예측 점수 계산
    current_user_prediction = user_based_prediction[-1]  # 마지막 행이 현재 사용자의 예측 점수

    # 상위 top_k개의 아이템 추천
    top_item_indices = current_user_prediction.argsort()[-top_k:][::-1]

    # 추천된 아이템의 세부 정보 추출
    top_recipes = [{"recipe_id": int(recipe_data.iloc[index]['recipe_id']),
                    "predicted_score": current_user_prediction[index]} for index in top_item_indices]

    return {"Top Collaborative Filtering Recommendations": top_recipes}


# 협업 필터링 + 아이템 기반 추천 합친 하이브리드
@router.post("/hybrid")
async def hybrid_recommendation(user_data: UserPreference, top_k: int = 10):
    # 각 방식으로부터 추천 받기
    collaborative_recommendations = await collaborative_filtering_recommendation(user_data, top_k=top_k//2)
    item_based_recommendations = get_recommendations_based_on_preference(user_data, top_k=top_k//2)

    # 추천 목록 합치기
    combined_recommendations = collaborative_recommendations[
                                   "Top Collaborative Filtering Recommendations"] + item_based_recommendations

    # 중복 제거
    seen_ids = set()
    unique_recommendations = []
    for rec in combined_recommendations:
        if rec['recipe_id'] not in seen_ids:
            seen_ids.add(rec['recipe_id'])
            unique_recommendations.append(rec)

    # 중복 제거 후 top_k보다 추천 목록이 적으면 추가로 아이템 추천 받기
    while len(unique_recommendations) < top_k:
        additional_recommendation = get_recommendations_based_on_preference(user_data, top_k=1)
        if additional_recommendation[0]['recipe_id'] not in seen_ids:
            seen_ids.add(additional_recommendation[0]['recipe_id'])
            unique_recommendations.append(additional_recommendation[0])

    random.shuffle(unique_recommendations)

    # 기존의 레시피 리스트를 recipe_id만 포함하도록 변환
    unique_recommendations = [rec["recipe_id"] for rec in unique_recommendations]

    return  unique_recommendations


# 조인 테스트
@router.get("/test")
async def test():
    # DB에서 모든 사용자의 mbti 정보 가져오기
    query = select([
        member.c.id,
        member.c.weight,
        member.c.gender,
        member.c.age,
        member.c.activity_level,
        member.c.mbti_id,
        mbti.c.korean_main_dish,
        mbti.c.western_main_dish,
        mbti.c.side_dish,
        mbti.c.dessert,
        mbti.c.daily_food,
        mbti.c.festival_food,
        mbti.c.convenience_food,
        mbti.c.snack_food,
        mbti.c.etc_food,
        mbti.c.meat,
        mbti.c.vegetable_seafood,
        mbti.c.processed_food,
        mbti.c.health_food,
        mbti.c.grain,
        mbti.c.low_cook,
        mbti.c.high_cook,
        mbti.c.water_cook,
        mbti.c.raw_cook,
        mbti.c.etc_cook
    ]).select_from(member.join(mbti, member.c.mbti_id == mbti.c.id))

    all_users_data = await database.fetch_all(query)

    return all_users_data[0]
