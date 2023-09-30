package com.foody.recommend.controller;

import com.foody.recipe.dto.response.RecipeListResponse;
import com.foody.recommend.service.RecommendService;
import com.foody.security.util.LoginInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
@RestController
public class RecommendController {

    private final RecommendService recommendService;


    //TODO : 냉장고 재료 + 취향, 냉장고 재료 + 영양소, 취향 + 영양소
    // 냉장고 재료 기반 추천 API
    @GetMapping("/ingredients")
    public ResponseEntity<List<RecipeListResponse>> recommendByIngredients(@AuthenticationPrincipal LoginInfo loginInfo) {

        log.debug("{} request recommend by ingredients", loginInfo.email());
        List<RecipeListResponse> recommendItemList = recommendService.findRecommendItemByIngredients(loginInfo.email());

        return ResponseEntity.ok().body(recommendItemList);
    }

    @GetMapping("/preference")
    public ResponseEntity<List<RecipeListResponse>> recommendByPreference(@AuthenticationPrincipal LoginInfo loginInfo) {

        log.debug("{} request recommend by preference" , loginInfo.email());

        return ResponseEntity.noContent().build();
    }

}
