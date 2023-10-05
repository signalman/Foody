package com.foody.nutrient.dto.request;

public record NutrientRequest(
    double energy,
    double carbohydrates, // 탄수화물
    double protein, // 단백질
    double dietaryFiber, // 식이섬유
    double calcium, // 칼슘
    double sodium, // 나트륨
    double iron, // 철분
    double fats, // 지방
    double vitaminA, // 비타민A
    double vitaminC // 비타민C
) {
}
