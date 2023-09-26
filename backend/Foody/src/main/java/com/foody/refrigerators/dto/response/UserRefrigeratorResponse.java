package com.foody.refrigerators.dto.response;

import com.foody.refrigerators.entity.RefrigeratorIngredient;

import java.time.format.DateTimeFormatter;

public record UserRefrigeratorResponse(
        Long ingredientId,
        String ingredientName,
        Long ingredientCategoryId,
        String createDate
) {
    public UserRefrigeratorResponse(RefrigeratorIngredient refrigeratorIngredient) {
        this(
                refrigeratorIngredient.getIngredient().getId(),
                refrigeratorIngredient.getIngredient().getIngredientName(),
                refrigeratorIngredient.getIngredient().getIngredientCategory().getId(),
                refrigeratorIngredient.getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY.MM.dd"))
        );
    }
}
