package com.foody.food.dto.response;

import com.foody.food.entity.FoodSearch;

public record FoodSearchResponse(
    String name,
    double energy,
    double carbohydrates,
    double protein,
    double dietaryFiber,
    double calcium,
    double sodium,
    double iron,
    double fats,
    double vitaminA,
    double vitaminC
) {
    public FoodSearchResponse(FoodSearch foodSearch){
        this(foodSearch.getName(), foodSearch.getEnergy(), foodSearch.getCarbohydrates(),
            foodSearch.getProtein(), foodSearch.getDietaryFiber(), foodSearch.getCalcium(),
            foodSearch.getSodium(), foodSearch.getIron(), foodSearch.getFats(),
            foodSearch.getVitaminA(), foodSearch.getVitaminC());
    }

}
