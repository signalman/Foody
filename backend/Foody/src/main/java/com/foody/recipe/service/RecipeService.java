package com.foody.recipe.service;

import com.foody.global.exception.ErrorCode;
import com.foody.recipe.dto.RecipeResponse;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.exception.RecipeException;
import com.foody.bookmark.repository.BookmarkRepository;
import com.foody.recipe.repository.RecipeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final BookmarkRepository bookmarkRepository;

    public RecipeResponse findById(long id) {

        Recipe recipe = recipeRepository.findById(id)
                                        .orElseThrow(() -> new RecipeException(
                                            ErrorCode.RECIPE_NOT_FOUND));

        return new RecipeResponse(recipe);
    }

    public List<RecipeResponse> findRecipeListByRecommend(List<Long> recipeIdList) {

        List<Recipe> recipes = recipeRepository.findAllById(recipeIdList);

        if (recipeIdList.size() != recipes.size()) {
            throw new RecipeException(ErrorCode.RECIPE_NOT_FOUND);
        }

        return recipes.stream()
                      .map(RecipeResponse::new)
                      .collect(Collectors.toList());
    }
}
