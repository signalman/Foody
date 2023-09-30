package com.foody.mealplan.controller;

import com.foody.mealplan.dto.request.MealPlanRequest;
import com.foody.mealplan.dto.response.MealPlanResponse;
import com.foody.mealplan.service.MealPlanService;
import com.foody.security.util.LoginInfo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/mealplan")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    /* 오늘부터 최근 20일간 식사 기록이 있는지 여부 */
    @GetMapping("/recent")
    public ResponseEntity<List<LocalDate>> getLast20DaysRecords(@AuthenticationPrincipal LoginInfo loginInfo){
        List<LocalDate> dates = mealPlanService.findLast20DaysRecords(loginInfo);
        return ResponseEntity.ok()
                             .body(dates);
    }


    /* 날짜별 식단 조회 */
    @GetMapping
    public ResponseEntity<MealPlanResponse> getMealPlanByDate(@AuthenticationPrincipal LoginInfo loginInfo, @RequestParam String date){
        MealPlanResponse mealPlanResponse =  mealPlanService.getMealPlanByDate(date, loginInfo);
        log.info("mealPlanResponseList: {}", mealPlanResponse);
        return ResponseEntity.ok().body(mealPlanResponse);
    }


    /* 해당되는 끼니의 음식 추가 */
    @PostMapping("/food")
    public ResponseEntity<String> registMealPlan(
        @AuthenticationPrincipal LoginInfo loginInfo,
        @RequestBody MealPlanRequest mealPlanRequest){
        mealPlanService.registMealPlan(loginInfo, mealPlanRequest);
        return ResponseEntity.noContent().build();
    }




}
