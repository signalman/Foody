package com.foody.mealplan.entity;

import com.foody.global.entity.BaseEntity;
import com.foody.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Enumerated(value = EnumType.STRING)
    private MealType mealType;

    @ManyToOne
    @JoinColumn(name = "mealplan_food_id")
    private MealPlan_Food mealPlanFood;

    private LocalDateTime dateTime; //ex) 13:40
}
