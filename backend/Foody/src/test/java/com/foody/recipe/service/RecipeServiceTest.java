package com.foody.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.foody.recipe.dto.RecipeResponse;
import com.foody.util.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RecipeServiceTest extends ServiceTest {

    @Autowired
    private RecipeService recipeService;

    @Test
    @DisplayName("레시피 조회된다")
    void t1() throws Exception {

        long id = 393505;
        RecipeResponse recipeResponse = recipeService.findById(id);

        System.out.println(recipeResponse);
        assertEquals(recipeResponse.name(), "브라우니");
    }

    @Test
    @DisplayName("여러 레시피 조회된다")
    void t2() throws Exception {

        List<Long> ids = List.of(396244L, 395162L, 393505L);

        List<RecipeResponse> recipeResponseList = recipeService.findRecipeListByRecommend(ids);

        assertEquals(recipeResponseList.size(), 3);
    }

}