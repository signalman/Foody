package com.foody.refrigerators.dto.response;

import com.foody.refrigerators.entity.Ingredient;

public record SearchIngredientResponse(
        Long ingredientId,
        String ingredientName,
        Long ingredientCategoryId
) {
    public SearchIngredientResponse(Ingredient ingredient) {
        this(
                ingredient.getId(),
                ingredient.getIngredientName(),
                ingredient.getIngredientCategory().getId()
        );
    }
}
