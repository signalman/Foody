package com.foody.mealplan.dto.request;

import com.foody.food.dto.request.FoodRequest;
import com.foody.mealplan.entity.MealType;
import java.util.List;

public record MealPlanRequest(
    MealType type, //아침, 점심, 저녁, 간식
    String date,
    List<FoodRequest> foodRequestList
) {
}
