package com.foody.refrigerators.entity;

import com.foody.global.entity.BaseEntity;
import com.foody.member.entity.Member;
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
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    public static RefrigeratorIngredient from(Member member, Ingredient ingredient) {
        return RefrigeratorIngredient.builder()
                .member(member)
                .ingredient(ingredient)
                .build();
    }

}
