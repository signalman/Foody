package com.foody.mealplan.dto.response;

import com.foody.mealplan.entity.MealPlan;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record MealPlanResponse(

    MealResponse breakfast,
    MealResponse lunch,
    MealResponse dinner,
    MealResponse snack,
    LocalDate date
) {
    public MealPlanResponse(){
        this(new MealResponse(), new MealResponse(), new MealResponse(), new MealResponse(), LocalDate.MIN);
    }
    public MealPlanResponse(MealPlan mealPlan){
        this(
            new MealResponse(mealPlan.getBreakfast()),
            new MealResponse(mealPlan.getLunch()),
            new MealResponse(mealPlan.getDinner()),
            new MealResponse(mealPlan.getSnack()),
            mealPlan.getDate()
        );
    }

    public static List<MealPlanResponse> fromMealPlans(List<MealPlan> mealPlans) {
        return mealPlans.stream()
                               .map(mealPlan -> new MealPlanResponse(mealPlan))
                               .collect(Collectors.toList());
    }
}
