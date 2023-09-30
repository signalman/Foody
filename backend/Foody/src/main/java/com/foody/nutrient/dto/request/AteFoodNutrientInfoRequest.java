package com.foody.nutrient.dto.request;

import com.foody.mealplan.entity.MealType;
import java.time.LocalDateTime;

public record AteFoodNutrientInfoRequest(
    LocalDateTime time,
    MealType mealType
) {

}
