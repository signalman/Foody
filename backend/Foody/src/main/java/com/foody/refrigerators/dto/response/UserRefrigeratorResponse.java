package com.foody.refrigerators.dto.response;

public record UserRefrigeratorResponse(
        Long IngredientId,
        String Ingredient,
        Long CategoryId,
        String CreateDate
) {

}
