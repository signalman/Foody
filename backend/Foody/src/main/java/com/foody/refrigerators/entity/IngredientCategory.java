package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class IngredientCategory extends BaseEntity {
    @Column(nullable = false)
    String ingredientCategoryName;

    @Column(nullable = false)
    CategoryType categoryType;

//    @Column(nullable = false)
    String ingredientCategoryIconImg;

}
