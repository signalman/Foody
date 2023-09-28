package com.foody.mealplan.controller;

import com.foody.mealplan.dto.response.MealPlanResponse;
import com.foody.mealplan.service.MealPlanService;
import com.foody.security.util.LoginInfo;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/mealplan")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @GetMapping("/")
    public ResponseEntity<List<MealPlanResponse>> getMealPlanByDate(@AuthenticationPrincipal LoginInfo loginInfo, @RequestParam String date){
        List<MealPlanResponse> mealPlanResponseList =  mealPlanService.getMealPlanByDate(date, loginInfo);
        return ResponseEntity.ok().body(mealPlanResponseList);
    }


}
