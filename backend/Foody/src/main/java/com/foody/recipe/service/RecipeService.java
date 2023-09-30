package com.foody.recipe.service;

import com.foody.bookmark.service.BookmarkFacade;
import com.foody.global.exception.ErrorCode;
import com.foody.recipe.dto.response.RecipeListResponse;
import com.foody.recipe.dto.response.RecipeResponse;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.exception.RecipeException;
import com.foody.recipe.repository.RecipeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final BookmarkFacade bookmarkFacade;
    private final StringRedisTemplate redisTemplate;

    @Transactional
    public RecipeResponse findById(long id, String email) {

        Recipe recipe = recipeRepository.findById(id)
                                        .orElseThrow(() -> new RecipeException(
                                            ErrorCode.RECIPE_NOT_FOUND));

        boolean isBookmarked = bookmarkFacade.existsByMemberEmailAndRecipe(email, id);
        String key = email + ":" + id;
        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            storeUserRecipeViewKey(key);
            userPreferenceUpdate(recipe, email);
        }
        return new RecipeResponse(recipe, isBookmarked);
    }

    private void userPreferenceUpdate(Recipe recipe, String email) {
        bookmarkFacade.updatePreference(email, recipe, 1);
    }

    private void storeUserRecipeViewKey(String key) {
        redisTemplate.opsForValue().set(key, "true");
    }

    public List<RecipeListResponse> findRecipeListByRecommend(List<Long> recipeIdList) {

        List<Recipe> recipes = recipeRepository.findAllById(recipeIdList);

        if (recipeIdList.size() != recipes.size()) {
            throw new RecipeException(ErrorCode.RECIPE_NOT_FOUND);
        }

        return recipes.stream()
                      .map(RecipeListResponse::new)
                      .collect(Collectors.toList());
        //TODO : 로직 수정
    }

    public Recipe getEntityById(long id) {

        return recipeRepository.findById(id)
                               .orElseThrow(() -> new RecipeException(ErrorCode.RECIPE_NOT_FOUND));
    }


}
