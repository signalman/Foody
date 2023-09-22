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

    private double energy; // 칼로리
    private double carbohydrates; // 탄수화물
    private double protein; // 단백질
    private double dietaryFiber; // 식이섬유
    private double calcium; // 칼슘
    private double sodium; // 나트륨
    private double iron; // 철분
    private double fats; // 지방
    private double vitaminA; // 비타민A
    private double vitaminC; // 비타민C
}
