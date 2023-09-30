package com.foody.mealplan.service;

import com.foody.food.dto.request.FoodRequest;
import com.foody.food.entity.Food;
import com.foody.global.exception.ErrorCode;
import com.foody.global.util.FoodyDateFormatter;
import com.foody.mealplan.dto.request.MealPlanRequest;
import com.foody.mealplan.dto.response.MealPlanResponse;
import com.foody.mealplan.entity.Meal;
import com.foody.mealplan.entity.MealPlan;
import com.foody.mealplan.entity.MealType;
import com.foody.mealplan.exception.MealPlanException;
import com.foody.mealplan.repository.MealPlanRepository;
import com.foody.member.entity.Member;
import com.foody.member.exception.MemberException;
import com.foody.member.repository.MemberRepository;
import com.foody.member.service.MemberService;
import com.foody.security.util.LoginInfo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MealPlan findById(long mealPlanId) {
        return mealPlanRepository.findById(mealPlanId)
                                 .orElseThrow(() -> new MealPlanException(
                                     ErrorCode.MEAL_PLAN_NOT_FOUND));
    }

    public MealPlan findByDateAndMemberId(LocalDate date, Long memberId){
        return mealPlanRepository.findByDateAndMemberId(date, memberId)
                                 .orElseThrow(
                                     () -> new MealPlanException(ErrorCode.MEAL_PLAN_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public MealPlanResponse getMealPlanByDate(String date, LoginInfo loginInfo) {

        Member member = memberRepository.findByEmail(loginInfo.email()).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));
        LocalDate localDate = FoodyDateFormatter.toLocalDate(date);

        MealPlan mealplan = findByDateAndMemberId(localDate, member.getId());
        return new MealPlanResponse(mealplan);
    }

    private Meal getMealByType(MealPlan mealPlan, MealType type) {
        switch (type) {
            case BREAKFAST:
                return mealPlan.getBreakfast();
            case LUNCH:
                return mealPlan.getLunch();
            case DINNER:
                return mealPlan.getDinner();
            case SNACK:
                return mealPlan.getSnack();
            default:
                throw new MealPlanException(ErrorCode.MEAL_TYPE_NOT_FOUND);
        }
    }


    @Transactional
    public void registMealPlan(LoginInfo loginInfo, MealPlanRequest mealPlanRequest) {
        Member member = memberRepository.findByEmail(loginInfo.email()).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));
        LocalDate localDate = FoodyDateFormatter.toLocalDate(mealPlanRequest.date());

        MealPlan mealPlan = mealPlanRepository.findByDateAndMemberId(localDate, member.getId())
                                              .orElseGet(() -> {
                                                  MealPlan newMealPlan = new MealPlan(member, localDate);
                                                  return mealPlanRepository.save(newMealPlan);
                                              });

        List<FoodRequest> foodRequests = mealPlanRequest.foodRequestList();
        Meal meal = getMealByType(mealPlan, mealPlanRequest.type());

        for (FoodRequest foodRequest : foodRequests) {
            meal.getFoods()
                .add(Food.fromRequest(foodRequest, meal));
        }
        meal.updateTime(LocalTime.now());
    }

    @Transactional(readOnly = true)
    public List<LocalDate> findLast20DaysRecords(LoginInfo loginInfo) {
        LocalDate startDate = LocalDate.now().minusDays(19);
        Member member = memberRepository.findByEmail(loginInfo.email()).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));
        return mealPlanRepository.findDatesByMemberIdAndDateAfter(member.getId(), startDate);
    }
}