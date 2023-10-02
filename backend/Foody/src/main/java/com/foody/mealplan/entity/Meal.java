package com.foody.mealplan.entity;

import com.foody.food.entity.Food;
import com.foody.global.entity.BaseEntity;
import com.foody.nutrient.entity.Nutrient;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal extends BaseEntity {

    @OneToMany(mappedBy = "meal", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();

    private LocalTime time = LocalTime.parse(
        LocalTime.MIN.format(DateTimeFormatter.ofPattern("HH:mm")));

    private String repImg = "";

    public void updateTime(LocalTime time){
        this.time = LocalTime.parse(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public void updateImage(String url){
        this.repImg = url;
    }

    public Nutrient totalMealNutrient(){
        double energy = 0.0;
        double carbohydrates = 0.0;
        double protein = 0.0;
        double dietaryFiber = 0.0;
        double calcium = 0.0;
        double sodium = 0.0;
        double iron = 0.0;
        double fats = 0.0;
        double vitaminA = 0.0;
        double vitaminC = 0.0;

        for (Food food : foods) {
            Nutrient nutrient = food.getNutrient();
            energy += nutrient.getEnergy();
            carbohydrates += nutrient.getCarbohydrates();
            protein += nutrient.getProtein();
            dietaryFiber += nutrient.getDietaryFiber();
            calcium += nutrient.getCalcium();
            sodium += nutrient.getSodium();
            iron += nutrient.getIron();
            fats += nutrient.getFats();
            vitaminA += nutrient.getVitaminA();
            vitaminC += nutrient.getVitaminC();
        }

        return new Nutrient(
            roundToOneDecimalPlace(energy),
            roundToOneDecimalPlace(carbohydrates),
            roundToOneDecimalPlace(protein),
            roundToOneDecimalPlace(dietaryFiber),
            roundToOneDecimalPlace(calcium),
            roundToOneDecimalPlace(sodium),
            roundToOneDecimalPlace(iron),
            roundToOneDecimalPlace(fats),
            roundToOneDecimalPlace(vitaminA),
            roundToOneDecimalPlace(vitaminC)
        );
    }

    private double roundToOneDecimalPlace(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    public void updateFoods(List<Food> foods){
        List<Food> originFoods = new ArrayList<>(this.foods);
        for (Food food : originFoods) {
            food.removeMeal();
        }
        this.foods.clear();

        for (Food food : foods) {
            food.assignMeal(this);
        }
        this.foods.addAll(foods);
    }
}
