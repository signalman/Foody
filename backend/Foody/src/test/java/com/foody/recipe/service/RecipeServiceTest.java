package com.foody.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.foody.recipe.dto.RecipeResponse;
import com.foody.recipe.exception.RecipeException;
import com.foody.util.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class RecipeServiceTest extends ServiceTest {

    @Autowired
    private RecipeService recipeService;

    String email = "lkm454545@gmail.com";
    @Test
    @Transactional
    @DisplayName("레시피 조회된다")
    void t1() throws Exception {

        memberInfoGenerator();

        long id = 393505;
        RecipeResponse recipeResponse = recipeService.findById(id, email);

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

    @Test
    @DisplayName("없는 레시피 조회시 custom error 반환한다")
    void t3() throws Exception {

        long inValidId = 9999999999L;

        assertThrows(RecipeException.class, () -> recipeService.findById(inValidId, email));

    }

}