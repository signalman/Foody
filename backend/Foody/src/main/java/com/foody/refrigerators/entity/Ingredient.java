package com.foody.refrigerators.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id @GeneratedValue
    long id;
    @Column(nullable = false)
    String ingredientName;
    @Column
    String iconImg;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_category_id")
    IngredientCategory ingredientCategory;
    @Enumerated
    IngredientType ingredientType;

    public static Ingredient from(String ingredientName, IngredientCategory ingredientCategory) {
        return Ingredient.builder()
                .ingredientName(ingredientName)
                .ingredientCategory(ingredientCategory)
                .iconImg(ingredientCategory.getIngredientCategoryIconImg())
                .ingredientType(IngredientType.CUSTOM).build();
    }
}
