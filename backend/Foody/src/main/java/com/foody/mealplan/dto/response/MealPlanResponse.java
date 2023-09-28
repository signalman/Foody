package com.foody.mealplan.dto.response;

import com.foody.food.dto.response.FoodResponse;
import com.foody.mealplan.entity.MealPlan;

public record MealPlanResponse(
    FoodResponse foodResponses,
    String mealType
) {

    public MealPlanResponse(MealPlan mealPlan) {
        this(
            new FoodResponse(mealPlan.getMealPlanFood()
                                     .getFood()),
            mealPlan.getMealType().name()
        );
    }
}
