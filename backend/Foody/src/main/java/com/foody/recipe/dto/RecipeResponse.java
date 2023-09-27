package com.foody.recipe.dto;

import com.foody.recipe.entity.Recipe;
import com.foody.recipe.util.RecipeUtils;
import java.util.List;

public record RecipeResponse(
    long id,
    String name,
    List<String> steps,
    List<IngredientUnit> ingredient,
    String url,
    String difficulty,
    int servers,
    double energy,
    double carbohydrates,
    double protein,
    double dietaryFiber,
    double calcium,
    double sodium,
    double iron,
    double fats,
    double vitaminA,
    double vitaminC

    ) {

    public RecipeResponse(Recipe recipe) {
        this(
            recipe.getId(),
            recipe.getName(),
            RecipeUtils.getDescriptionFromString(recipe.getDescription()),
            RecipeUtils.formatIngredients(recipe.getIngredient()),
            recipe.getUrl(),
            recipe.getDifficulty(),
            (int) recipe.getServers(),
            recipe.getEnergy(),
            recipe.getCarbohydrates(),
            recipe.getProtein(),
            recipe.getDietaryFiber(),
            recipe.getCalcium(),
            recipe.getSodium(),
            recipe.getIron(),
            recipe.getFats(),
            recipe.getVitaminA(),
            recipe.getVitaminC()
        );

    }
}
