package com.foody.food.dto.response;

import com.foody.food.entity.Food;
import com.foody.nutrient.dto.response.NutrientResponse;
import java.util.List;
import java.util.stream.Collectors;

public record FoodResponse(
    String foodImage,
    String name,
    NutrientResponse nutrient
) {
    public FoodResponse(){
        this("","", new NutrientResponse());
    }
    public FoodResponse(Food food) {
        this(food.getImageUrl(), food.getName(), new NutrientResponse(food.getNutrient()));
    }
    public static List<FoodResponse> fromFoods(List<Food> foods) {
        return foods.stream()
                    .map(food -> new FoodResponse(food))
                    .collect(Collectors.toList());
    }
}
