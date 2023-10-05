package com.foody.recipe.controller;

import com.foody.recipe.dto.response.RecipeResponse;
import com.foody.recipe.service.RecipeService;
import com.foody.security.util.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipe")
@RestController
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@AuthenticationPrincipal LoginInfo loginInfo, @PathVariable Long id) {

        log.debug(" {} request recipe information", loginInfo.email());
        RecipeResponse recipeResponse = recipeService.findById(id, loginInfo.email());

        return ResponseEntity.ok().body(recipeResponse);
    }


}
