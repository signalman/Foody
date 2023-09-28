package com.foody.mealplan.entity;

import com.foody.food.entity.Food;
import com.foody.global.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 음식과 식단의 중간테이블
 * 한 음식이 여러개의 식단에 들어갈 수 있고
 * 한 식단이 여러개의 음식에 들어갈 수 있음.
 * */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MealPlan_Food extends BaseEntity {

    @OneToMany(mappedBy = "mealPlanFood", fetch = FetchType.LAZY)
    private List<MealPlan> mealPlan = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
