package com.foody.refrigerators.dto.request;

public record InsertCustomIngredientRequest (
        Long ingredientCategoryId,
        String ingredientName
) {
}
