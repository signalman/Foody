package com.foody.mealplan.dto.response;

import com.foody.food.dto.response.FoodResponse;
import com.foody.mealplan.entity.Meal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record MealResponse(
    List<FoodResponse> foods,
    LocalTime time
) {
    public MealResponse(){
        this(new ArrayList<>(), LocalTime.MIN);
    }
    public MealResponse(Meal meal) {
        this(
            FoodResponse.fromFoods(meal.getFoods()),
            meal.getTime());
    }
}
