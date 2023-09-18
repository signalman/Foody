package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient extends BaseEntity {
    @Column(nullable = false)
    String ingredientName;
    @Column
    String iconImg;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    IngredientCategory ingredientCategory;
}
