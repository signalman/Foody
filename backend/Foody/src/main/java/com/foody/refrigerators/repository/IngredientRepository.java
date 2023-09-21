package com.foody.refrigerators.repository;

import com.foody.refrigerators.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientsByIngredientNameContaining(String IngredientName);
}
