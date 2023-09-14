package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class IngredientCategory extends BaseEntity {
    @Column(nullable = false)
    String CategoryName;
}
