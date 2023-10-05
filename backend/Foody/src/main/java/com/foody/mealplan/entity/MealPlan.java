package com.foody.mealplan.entity;

import com.foody.food.entity.Food;
import com.foody.global.entity.BaseEntity;
import com.foody.member.entity.Member;
import com.foody.nutrient.entity.Nutrient;
import java.time.LocalDate;
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
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MealPlan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "breakfast_id")
    private Meal breakfast;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lunch_id")
    private Meal lunch;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dinner_id")
    private Meal dinner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "snack_id")
    private Meal snack;

    private LocalDate date; //ex) 2023-11-09

    public MealPlan(Member member, LocalDate localDate){
        this.member = member;
        this.breakfast = new Meal();
        this.lunch = new Meal();
        this.dinner = new Meal();
        this.snack = new Meal();
        this.date = localDate;
    }
    public Nutrient totalMealPlanNutrient(){
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

        Meal[] meals = new Meal[]{breakfast, lunch, dinner, snack};

        for (Meal meal : meals) {
            for (Food food : meal.getFoods()) {
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
}
