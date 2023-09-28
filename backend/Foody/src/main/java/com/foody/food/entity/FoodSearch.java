package com.foody.food.entity;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.Column;
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
    private long id;

    @CsvBindByName(column = "food_id")
    private long foodId;

    @Column
    @CsvBindByName(column = "food_name")
    private String foodName;

    @Column
    @CsvBindByName(column = "energy")
    private double energy;

    @Column
    @CsvBindByName(column = "carbohydrates")
    double carbohydrates; // 탄수화물, g

    @Column
    @CsvBindByName(column = "protein")
    double protein; // 단백질, g

    @Column
    @CsvBindByName(column = "dietary_fiber")
    double dietary_fiber; // 식이섬유, g

    @Column
    @CsvBindByName(column = "calcium")
    double calcium; // 칼슘, mg

    @Column
    @CsvBindByName(column = "sodium")
    double sodium; // 나트륨, mg

    @Column
    @CsvBindByName(column = "iron")
    double iron; // 철분, mg

    @Column
    @CsvBindByName(column = "fats")
    double fats; // 지방 g

    @Column
    @CsvBindByName(column = "vitamin_a")
    double vitamin_a; // 비타민A, μg

    @Column
    @CsvBindByName(column = "vitamin_c")
    double vitamin_c; // 비타민C, mg

}
