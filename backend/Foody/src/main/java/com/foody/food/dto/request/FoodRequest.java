package com.foody.food.dto.request;

import com.foody.nutrient.dto.request.NutrientRequest;

public record FoodRequest(
    String name,
    NutrientRequest nutrientRequest
) {

}
