package com.foody.refrigerators.dto.response;

public record ReceiptIngredientResponse(
        Long ingredientId,
        String ingredientName,
        Long ingredientCategory
) {
}
