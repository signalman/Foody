package com.foody.mealplan.entity;

import com.foody.global.entity.BaseEntity;
import com.foody.member.entity.Member;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "breakfast_id")
    private Meal breakfast;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lunch_id")
    private Meal lunch;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dinner_id")
    private Meal dinner;

    @OneToOne(cascade = CascadeType.PERSIST)
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
}
