package com.foody.nutrient.dto.response;

import com.foody.nutrient.entity.Nutrient;

public record NutrientResponse(
    double energy,
    double carbohydrates, // 탄수화물
    double protein, // 단백질
    double dietaryFiber, // 식이섬유
    double calcium,// 칼슘
    double sodium, // 나트륨
    double iron, // 철분
    double fats, // 지방
    double vitaminA, // 비타민A
    double vitaminC // 비타민C
) {
    public NutrientResponse(Nutrient nutrient) {
        this(
            nutrient.getEnergy(),
            nutrient.getCarbohydrates(),
            nutrient.getProtein(),
            nutrient.getDietaryFiber(),
            nutrient.getCalcium(),
            nutrient.getSodium(),
            nutrient.getIron(),
            nutrient.getFats(),
            nutrient.getVitaminA(),
            nutrient.getVitaminC()
        );
    }
}
