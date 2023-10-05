package com.foody.refrigerators.dto.request;

import java.util.List;

public record InsertIngredientRequest(
        List<Long> ingredients,
        List<CustomIngredientRequest> customIngredients
) {

}
