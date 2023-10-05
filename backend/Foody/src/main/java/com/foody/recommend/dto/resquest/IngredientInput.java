package com.foody.recommend.dto.resquest;

public record IngredientInput(
    String ingredients,
    int top_k
) {

}
