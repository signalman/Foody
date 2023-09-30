package com.foody.recommend.dto.resquest;

import com.foody.mbti.dto.response.MbtiResponse;
import com.foody.nutrient.dto.response.NutrientResponse;

public record CombineInfoForPreference(
    NutrientResponse user_deficiency,
    MbtiResponse user_preference
) {

}
