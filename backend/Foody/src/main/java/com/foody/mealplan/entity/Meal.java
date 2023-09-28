package com.foody.mealplan.entity;

import com.foody.food.entity.Food;
import com.foody.global.entity.BaseEntity;
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

    @OneToMany(mappedBy = "meal", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Food> foods = new ArrayList<>();

    private LocalTime time = LocalTime.parse(
        LocalTime.MIN.format(DateTimeFormatter.ofPattern("HH:mm")));

    public void updateTime(LocalTime time){
        this.time = LocalTime.parse(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
