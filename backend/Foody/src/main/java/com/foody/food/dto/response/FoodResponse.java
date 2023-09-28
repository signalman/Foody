package com.foody.food.dto.response;

import com.foody.food.entity.Food;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.nutrient.entity.Nutrient;

public record FoodResponse(
    String name,
    NutrientResponse nutrient
) {
    public FoodResponse(Food food) {
        this(food.getName(), new NutrientResponse(food.getNutrient()));
    }
}
