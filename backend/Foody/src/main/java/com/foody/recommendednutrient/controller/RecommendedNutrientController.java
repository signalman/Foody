package com.foody.recommendednutrient.controller;

import com.foody.recommendednutrient.dto.response.RecommendedNutrientResponse;
import com.foody.recommendednutrient.service.RecommendedNutrientService;
import com.foody.security.util.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommended")
public class RecommendedNutrientController {

    private final RecommendedNutrientService recommendedNutrientService;

    @GetMapping("/nutrient")
    public ResponseEntity<RecommendedNutrientResponse> getRecommendedNutrient(@AuthenticationPrincipal
    LoginInfo loginInfo) {
        log.debug("'{}' request recommendedNutrient", loginInfo.email());

        RecommendedNutrientResponse recommendedNutrientResponse = recommendedNutrientService.getRecommendedNutrient(
            loginInfo.email());

        return ResponseEntity.ok().body(recommendedNutrientResponse);
    }
}
