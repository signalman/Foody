package com.foody.recipe.dto.response;

import com.foody.recipe.entity.Recipe;

public record RecipeListResponse(
    long id,
    String name,
    String url
) {

    public RecipeListResponse(Recipe recipe) {
        this(
            recipe.getId(),
            recipe.getName(),
            recipe.getUrl()
        );
    }

}
