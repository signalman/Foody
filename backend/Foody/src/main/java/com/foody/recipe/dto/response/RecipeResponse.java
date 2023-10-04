package com.foody.recipe.dto.response;

import com.foody.recipe.dto.IngredientUnit;
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
    double fats,
    double dietaryFiber,
    double calcium,
    double sodium,
    double iron,
    double vitaminA,
    double vitaminC,
    boolean isBookmarked

    ) {

    public RecipeResponse(Recipe recipe, boolean isBookmarked) {
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
            recipe.getFats(),
            recipe.getDietaryFiber(),
            recipe.getCalcium(),
            recipe.getSodium(),
            recipe.getIron(),
            recipe.getVitaminA(),
            recipe.getVitaminC(),
            isBookmarked
        );

    }
}
