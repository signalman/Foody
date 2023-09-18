package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import com.foody.user.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefrigeratorIngredient extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    public static RefrigeratorIngredient from(User user, Ingredient ingredient) {
        return RefrigeratorIngredient.builder()
                .user(user)
                .ingredient(ingredient)
                .build();
    }

}
