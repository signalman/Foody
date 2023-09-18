package com.foody.refrigerators.dto.response;

import com.foody.refrigerators.entity.Ingredient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SearchIngredientResponse {
    long ingredientId;
    String ingredientName;

    public SearchIngredientResponse(Ingredient ingredient) {
        this.setIngredientId(ingredient.getId());
        this.setIngredientName(ingredient.getIngredientName());
    }
}
