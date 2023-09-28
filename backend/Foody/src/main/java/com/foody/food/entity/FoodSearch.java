package com.foody.food.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FoodSearch {
    @Id
    private Long id;
    private String name;
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

}
