package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
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
    @Enumerated
    IngredientType ingredientType;

    public static Ingredient from(String ingredientName, IngredientCategory ingredientCategory) {
        return Ingredient.builder()
                .ingredientName(ingredientName)
                .ingredientCategory(ingredientCategory)
                .ingredientType(IngredientType.CUSTOM).build();
    }
}
