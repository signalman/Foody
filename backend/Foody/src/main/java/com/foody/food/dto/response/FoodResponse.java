package com.foody.food.dto.response;

import com.foody.food.entity.Food;
import com.foody.nutrient.dto.response.NutrientResponse;
import java.util.List;
import java.util.stream.Collectors;

public record FoodResponse(
    String name,
    NutrientResponse nutrient
) {
    public FoodResponse(Food food) {
        this(food.getName(), new NutrientResponse(food.getNutrient()));
    }
    public static List<FoodResponse> fromFoods(List<Food> foods) {
        return foods.stream()
                    .map(FoodResponse::new)
                    .collect(Collectors.toList());
    }
}
