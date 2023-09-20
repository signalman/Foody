package com.foody.refrigerators.repository;

import com.foody.member.entity.Member;
import com.foody.refrigerators.entity.RefrigeratorIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefrigeratorIngredientRepository extends JpaRepository<RefrigeratorIngredient, Long> {
    public List<RefrigeratorIngredient> findAllByMember(Member member);
}
