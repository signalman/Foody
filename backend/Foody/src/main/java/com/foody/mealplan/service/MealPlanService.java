package com.foody.mealplan.service;

import com.foody.global.exception.ErrorCode;
import com.foody.mealplan.dto.response.MealPlanResponse;
import com.foody.mealplan.entity.MealPlan;
import com.foody.mealplan.exception.MealPlanException;
import com.foody.mealplan.repository.MealPlanRepository;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.security.util.LoginInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final MemberService memberService;

    public MealPlanResponse findById(long mealPlanId) {
        MealPlan mealPlan = mealPlanRepository.findById(mealPlanId)
                                              .orElseThrow(() -> new MealPlanException(
                                                  ErrorCode.MEAL_PLAN_NOT_FOUND));
        return new MealPlanResponse(mealPlan);
    }

    @Transactional(readOnly = true)
    public List<MealPlanResponse> getMealPlanByDate(String date, LoginInfo loginInfo) {

        Member member = memberService.findByEmail(loginInfo.email());
        LocalDate targetDate = LocalDate.parse(date); // "2023-11-20" 형식의 문자열을 LocalDate로 변환
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.atTime(23, 59, 59);
        List<MealPlan> mealPlans = mealPlanRepository.findByDateTimeBetweenAndMemberId(startOfDay, endOfDay, member.getId());
        return mealPlans.stream()
                 .map(mealPlan -> new MealPlanResponse(mealPlan))
                 .collect(Collectors.toList());
    }
}
