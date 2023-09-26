package com.foody.recipe.repository;

import com.foody.recipe.entity.Recipe;

import java.util.List;

public interface RecipeCustomRepository {
    void bulkInsert(List<Recipe> recipes);
}
