package com.foody.refrigerators.dto.request;

public record CustomIngredientRequest(
        Long ingredientCategoryId,
        String ingredientName
) {
}
