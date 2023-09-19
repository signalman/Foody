package com.foody.refrigerators.repository;

import com.foody.refrigerators.entity.CustomIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomIngredientRepository extends JpaRepository<CustomIngredient, Long> {
}
