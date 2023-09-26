package com.foody.recipe.service;

import com.foody.global.exception.ErrorCode;
import com.foody.recipe.dto.RecipeResponse;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.exception.RecipeException;
import com.foody.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeResponse findById(long id) {

        Recipe recipe = recipeRepository.findById(id)
                                        .orElseThrow(() -> new RecipeException(
                                            ErrorCode.RECIPE_NOT_FOUND));

        return new RecipeResponse(recipe);
    }
}
