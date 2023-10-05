package com.foody.recommend.dto.resquest;

import com.foody.nutrient.dto.response.NutrientResponse;

public record CombineInfoForRefrigerator(
    String ingredients,
    NutrientResponse user_deficiency
) {

}
