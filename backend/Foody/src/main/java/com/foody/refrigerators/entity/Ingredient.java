package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient extends BaseEntity {
    @Column(nullable = false)
    String ingredientName;
    @Column
    String iconImg;
    @ManyToOne
    @JoinColumn(nullable = false)
    IngredientCategory ingredientCategory;
}
