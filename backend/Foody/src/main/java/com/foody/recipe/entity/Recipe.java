package com.foody.recipe.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity(name = "recipe")
public class Recipe {

    @Id private long id;
    @Column private String name;
    @Column(columnDefinition = "TEXT") private String ingredient;
    @Column(columnDefinition = "TEXT") private String description;
    @Column private String url;
    @Column private String difficulty;
    @Column private String servers;
    @Column private String foodMethod;
    @Column private String foodSituation;
    @Column private String foodIngredients;
    @Column private String foodTypes;
}
