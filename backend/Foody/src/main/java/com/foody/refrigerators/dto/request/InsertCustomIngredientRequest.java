package com.foody.refrigerators.dto.request;

public record InsertCustomIngredientRequest (
        Long IngredientCategoryId,
        String ingredientName
) {
}
