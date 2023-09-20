package com.foody.refrigerators.repository;

import com.foody.member.entity.Member;
import com.foody.refrigerators.entity.CustomIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomIngredientRepository extends JpaRepository<CustomIngredient, Long> {

    public List<CustomIngredient> findAllByMember(Member member);
}
