package com.foody.food.entity;

import com.foody.global.entity.BaseEntity;
import com.foody.nutrient.entity.Nutrient;
import javax.persistence.Entity;
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
    @OneToOne
    private Nutrient nutrient;
}
