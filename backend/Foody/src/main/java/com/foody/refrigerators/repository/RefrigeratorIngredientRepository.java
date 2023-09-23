package com.foody.refrigerators.repository;

import com.foody.member.entity.Member;
import com.foody.refrigerators.entity.Ingredient;
import com.foody.refrigerators.entity.RefrigeratorIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorIngredientRepository extends JpaRepository<RefrigeratorIngredient, Long> {
    List<RefrigeratorIngredient> findAllByMember(Member member);
    Optional<RefrigeratorIngredient> findByMemberAndIngredient(Member member, Ingredient ingredient);
    boolean existsByMemberAndIngredient(Member member, Ingredient ingredient);
}
