package com.foody.recommendednutrient.entity;

import com.foody.global.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedNutrient extends BaseEntity {

    private float energy; // 칼로리
    private float carbohydrates; // 탄수화물
    private float protein; // 단백질
    private float dietaryFiber; // 식이섬유
    private float calcium; // 칼슘
    private float sodium; // 나트륨
    private float iron; // 철분
    private float fats; // 지방
    private float vitaminA; // 비타민A
    private float vitaminC; // 비타민C
}
