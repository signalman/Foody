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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        @RequestPart MealPlanRequest mealPlanRequest,
        @RequestPart(required = false) MultipartFile mealImage,
        @RequestPart(required = false) List<MultipartFile> foodImages
        ){
        log.info("mealPlanRequest: {}", mealPlanRequest);
        log.info("mealImage: {}", mealImage);
        for (MultipartFile foodImage : foodImages) {
            log.info("foodImage: {}", foodImage);
        }
        mealPlanService.registMealPlan(loginInfo, mealPlanRequest, mealImage, foodImages);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/food")
    public ResponseEntity<String> modifyMealPlan(
        @AuthenticationPrincipal LoginInfo loginInfo,
        @RequestPart MealPlanRequest mealPlanRequest,
        @RequestPart List<MultipartFile> foodImages
        ){
        mealPlanService.modifyMealPlan(loginInfo, mealPlanRequest, foodImages);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/food/text")
    public ResponseEntity<String> registFoodUsingText(
        @AuthenticationPrincipal LoginInfo loginInfo,
        @RequestPart MealPlanRequest mealPlanRequest
    ){
        mealPlanService.registMealPlanUsingText(loginInfo, mealPlanRequest);
        return ResponseEntity.noContent()
                             .build();
    }

    @DeleteMapping("/{date}/{type}")
    public ResponseEntity<String> deleteMealPlan(
        @AuthenticationPrincipal LoginInfo loginInfo,
        @PathVariable String date,
        @PathVariable String type){
        mealPlanService.deleteMealPlan(loginInfo, date, type);
        return ResponseEntity.noContent()
                             .build();
    }



}
