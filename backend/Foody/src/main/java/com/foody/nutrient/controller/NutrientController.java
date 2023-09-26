package com.foody.nutrient.controller;

import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.nutrient.service.NutrientService;
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
@RequestMapping("/api/v1/nutrient")
public class NutrientController {

    private final NutrientService nutrientService;

    @GetMapping("/")
    public ResponseEntity<NutrientResponse> getNutrient(@AuthenticationPrincipal
    LoginInfo loginInfo) {
        log.debug("'{}' request Nutrient", loginInfo.email());

        NutrientResponse nutrientResponse = nutrientService.getNutrient(
            loginInfo.email());

        return ResponseEntity.ok().body(nutrientResponse);
    }
}
