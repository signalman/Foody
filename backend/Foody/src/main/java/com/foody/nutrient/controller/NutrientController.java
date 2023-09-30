package com.foody.nutrient.controller;

import com.amazonaws.Response;
import com.foody.nutrient.dto.request.AteFoodNutrientInfoRequest;
import com.foody.nutrient.dto.request.NutrientTypeRequest;
import com.foody.nutrient.dto.response.NutrientByTypeResponse;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.nutrient.service.NutrientService;
import com.foody.security.util.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/type")
    public ResponseEntity<NutrientResponse> getTypeNutrient(@AuthenticationPrincipal LoginInfo loginInfo, @RequestBody
        NutrientTypeRequest nutrientTypeRequest){
        log.debug("{} request MealType Nutrient", loginInfo.email());

        NutrientResponse nutrientResponse = nutrientService.getMealNutrient(loginInfo.email(), nutrientTypeRequest.mealType());

        return ResponseEntity.ok().body(nutrientResponse);
    }

    @GetMapping("/nutrientInfo")
    public ResponseEntity<NutrientResponse> getNutrientInfo(@AuthenticationPrincipal LoginInfo loginInfo, @RequestBody
        AteFoodNutrientInfoRequest ateFoodNutrientInfoRequest) {
        log.debug("{} request NutrientInfo about type", loginInfo.email());

        NutrientResponse nutrientResponse = nutrientService.calcMealNutrient(loginInfo.email(), ateFoodNutrientInfoRequest.time(), ateFoodNutrientInfoRequest.mealType());

        return ResponseEntity.ok().body(nutrientResponse);
    }

    @GetMapping("/alltype")
    public ResponseEntity<NutrientByTypeResponse> getAllTypeNutrient(@AuthenticationPrincipal LoginInfo loginInfo) {
        log.debug("{} request All Type Nutrient", loginInfo.email());

        NutrientByTypeResponse nutrientByTypeResponse = nutrientService.getAllNutrient(loginInfo.email());

        return ResponseEntity.ok().body(nutrientByTypeResponse);
    }
}
