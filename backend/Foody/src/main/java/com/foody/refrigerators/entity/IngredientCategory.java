package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class IngredientCategory{
    @Id
    long id;
    @Column(nullable = false)
    String ingredientCategoryName;

    @Column(nullable = false)
    CategoryType categoryType;

    @Column(nullable = false)
    String ingredientCategoryIconImg;

}
