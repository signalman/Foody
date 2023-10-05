package com.foody.refrigerators.dto.response;

import com.foody.refrigerators.entity.CategoryType;
import com.foody.refrigerators.entity.RefrigeratorIngredient;

import java.time.format.DateTimeFormatter;

public record UserRefrigeratorResponse(
        Long ingredientId,
        String ingredientName,
        Long ingredientCategoryId,
        int categoryType,
        String iconImg,
        String createDate
) {
    public UserRefrigeratorResponse(RefrigeratorIngredient refrigeratorIngredient) {
        this(
                refrigeratorIngredient.getIngredient().getId(),
                refrigeratorIngredient.getIngredient().getIngredientName(),
                refrigeratorIngredient.getIngredient().getIngredientCategory().getId(),
                refrigeratorIngredient.getIngredient().getIngredientCategory().getCategoryType().getCategoryLevel(),
                refrigeratorIngredient.getIngredient().getIconImg(),
                refrigeratorIngredient.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        );
    }
}
