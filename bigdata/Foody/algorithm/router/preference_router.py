from fastapi import APIRouter
from pydantic import BaseModel, constr
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import random

router = APIRouter()

# 데이터 로딩
recipe_data = pd.read_csv('data/recipe_information_reprocessed.csv')
recipe_data_cleaned = recipe_data.dropna(subset=['ingredients_concat'])
recipe_data_jaccard = pd.read_csv('data/recipe_jaccard.csv')


class UserPreference(BaseModel):
    # 난이도
    difficulty: constr(regex="^(초급|중급|아무나)$")

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


@router.post("/preference")
def get_recommendations_based_on_preference(data: UserPreference, top_k: int = 5):
    # 사용자의 선호도 정보를 배열로 변환
    user_vector = np.array(list(data.dict().values()))

    recipe_vectors = recipe_data[list(data.dict().keys())].values

    # 각 레시피에 대한 코사인 유사도 계산
    similarities = cosine_similarity(user_vector.reshape(1, -1), recipe_vectors).flatten()

    # 가장 유사한 top_k 개의 레시피 인덱스 가져오기
    top_indices = similarities.argsort()[-top_k:][::-1]

    # 가장 높은 유사도 점수를 가진 레시피가 5개 이상인지 확인
    max_similarity = similarities[top_indices[0]]
    max_similarity_indices = [index for index in top_indices if similarities[index] == max_similarity]

    # 가장 높은 유사도 점수를 가진 레시피가 5개 이상이면, 그 중에서 랜덤하게 5개 선택
    if len(max_similarity_indices) > 5:
        max_similarity_indices = random.sample(max_similarity_indices, 5)

    # 가장 유사한 레시피와 그들의 세부 사항 추출
    top_recipes = [
        {
            "recipe_id": int(recipe_data.iloc[index]['id']),
            "ingredients": recipe_data.iloc[index]['ingredients_concat'],
            "similarity_score": similarities[index]
        }
        for index in max_similarity_indices
    ]

    return top_recipes
