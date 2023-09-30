package com.foody.nutrient.dto.response;

import com.foody.nutrient.entity.Nutrient;

public record NutrientByTypeResponse(
    NutrientResponse breakfast,
    NutrientResponse lunch,
    NutrientResponse dinner,
    NutrientResponse snack
) {
 public NutrientByTypeResponse(NutrientResponse breakfast, NutrientResponse lunch, NutrientResponse dinner, NutrientResponse snack) {
     this.breakfast = breakfast;
     this.lunch = lunch;
     this.dinner = dinner;
     this.snack = snack;
 }
}
