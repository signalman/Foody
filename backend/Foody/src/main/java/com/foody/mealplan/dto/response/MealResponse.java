package com.foody.mealplan.dto.response;

import com.foody.food.dto.response.FoodResponse;
import com.foody.mealplan.entity.Meal;
import com.foody.nutrient.dto.response.NutrientResponse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record MealResponse(
    NutrientResponse total,
    List<FoodResponse> foods,
    String imgUrl,
    LocalTime time
) {
    public MealResponse(){
        this(new NutrientResponse(), new ArrayList<>(),"", LocalTime.MIN);
    }
    public MealResponse(Meal meal) {
        this(
            new NutrientResponse(meal.totalMealNutrient()),
            FoodResponse.fromFoods(meal.getFoods()),
            meal.getRepImg(),
            meal.getTime());
    }
}
