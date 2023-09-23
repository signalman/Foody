package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import com.foody.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomIngredient extends BaseEntity {
    String customIngredientName;

    @ManyToOne(fetch = FetchType.LAZY)
    IngredientCategory ingredientCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    Member member;

    public static CustomIngredient from(Member member, IngredientCategory ingredientCategory, String name) {
        return CustomIngredient.builder()
                .member(member)
                .ingredientCategory(ingredientCategory)
                .customIngredientName(name).build();
    }

}
