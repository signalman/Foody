package com.foody.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FoodSearch {

    @Id @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private double energy;

    @Column
    double carbohydrates; // 탄수화물, g

    @Column
    double protein; // 단백질, g

    @Column
    double dietaryFiber; // 식이섬유, g

    @Column
    double calcium; // 칼슘, mg

    @Column
    double sodium; // 나트륨, mg

    @Column
    double iron; // 철분, mg

    @Column
    double fats; // 지방 g

    @Column
    double vitaminA; // 비타민A, μg

    @Column
    double vitaminC; // 비타민C, mg

}
