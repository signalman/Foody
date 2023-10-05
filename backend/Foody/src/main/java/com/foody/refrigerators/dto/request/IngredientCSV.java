package com.foody.refrigerators.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IngredientCSV {
    long ingredientId;
    String ingredientName;
    int ingredientType;
    long ingredientCategoryId;
    String iconImg;
}
