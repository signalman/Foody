package com.foody.nutrient.entity;

import com.foody.global.entity.BaseEntity;
import com.foody.nutrient.dto.request.NutrientRequest;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Nutrient extends BaseEntity {

    private double energy; // 칼로리, Kcal
    private double carbohydrates; // 탄수화물, g
    private double protein; // 단백질, g
    private double dietaryFiber; // 식이섬유, g
    private double calcium; // 칼슘, mg
    private double sodium; // 나트륨, mg
    private double iron; // 철분, mg
    private double fats; // 지방 g
    private double vitaminA; // 비타민A, μg
    private double vitaminC; // 비타민C, mg

    public static Nutrient fromNutrientRequest(NutrientRequest nutrientRequest) {
        return new Nutrient(nutrientRequest.energy(), nutrientRequest.carbohydrates(),
            nutrientRequest.protein(), nutrientRequest.dietaryFiber(), nutrientRequest.calcium(),
            nutrientRequest.sodium(), nutrientRequest.iron(), nutrientRequest.fats(),
            nutrientRequest.vitaminA(), nutrientRequest.vitaminC());
    }
}
