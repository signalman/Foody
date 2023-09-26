package com.foody.recipe.service;

import static org.junit.jupiter.api.Assertions.*;

import com.foody.recipe.dto.RecipeResponse;
import com.foody.util.ServiceTest;
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

}