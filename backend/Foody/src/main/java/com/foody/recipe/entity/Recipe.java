package com.foody.recipe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // openCsv때문에 열어놔야함
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
    @Column private double servers;
    @Column private String foodMethod;
    @Column private String foodSituation;
    @Column private String foodIngredients;
    @Column private String foodTypes;
    @Column private double energy;
    @Column private double carbohydrates;
    @Column private double protein;
    @Column private double dietaryFiber;
    @Column private double calcium;
    @Column private double sodium;
    @Column private double iron;
    @Column private double fats;
    @Column private double vitaminA;
    @Column private double vitaminC;
}
