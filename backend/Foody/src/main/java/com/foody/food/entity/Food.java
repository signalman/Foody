package com.foody.food.entity;

import com.foody.food.dto.request.FoodRequest;
import com.foody.global.entity.BaseEntity;
import com.foody.mealplan.entity.Meal;
import com.foody.nutrient.entity.Nutrient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Food extends BaseEntity {

    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public static Food fromRequest(FoodRequest foodRequest, Meal meal) {
        Nutrient getNutrient = Nutrient.fromNutrientRequest(foodRequest.nutrientRequest());
        return new Food(foodRequest.name(), getNutrient, meal);
    }
    public void assignMeal(Meal meal){
        if (this.meal != null) {
            this.meal.getFoods().remove(this);
        }
        this.meal = meal;
        meal.getFoods().add(this);
    }

    public void removeMeal() {
        if (this.meal != null) {
            this.meal.getFoods().remove(this);
        }
        this.meal = null;
    }
}
