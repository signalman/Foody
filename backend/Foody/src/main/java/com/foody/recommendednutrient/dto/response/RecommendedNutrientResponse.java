package com.foody.recommendednutrient.dto.response;

import com.foody.recommendednutrient.entity.RecommendedNutrient;

public record RecommendedNutrientResponse(
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
    public RecommendedNutrientResponse(RecommendedNutrient recommendedNutrient) {
        this(
            recommendedNutrient.getEnergy(),
            recommendedNutrient.getCarbohydrates(),
            recommendedNutrient.getProtein(),
            recommendedNutrient.getDietaryFiber(),
            recommendedNutrient.getCalcium(),
            recommendedNutrient.getSodium(),
            recommendedNutrient.getIron(),
            recommendedNutrient.getFats(),
            recommendedNutrient.getVitaminA(),
            recommendedNutrient.getVitaminC()
        );
    }
}
