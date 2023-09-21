package com.foody.recommendednutrient.dto.request;

public record CreateNutrientRequest(
     float carbohydrates, // 탄수화물
     float protein, // 단백질
     float dietaryFiber, // 식이섬유
     float calcium, // 칼슘
     float sodium, // 나트륨
     float iron, // 철분
     float fats, // 지방
     float vitaminA, // 비타민A
     float vitaminC // 비타민C
) {
}
