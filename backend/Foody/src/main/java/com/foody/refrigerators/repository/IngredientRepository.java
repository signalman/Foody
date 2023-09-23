package com.foody.refrigerators.repository;

import com.foody.refrigerators.entity.Ingredient;
import com.foody.refrigerators.entity.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientsByIngredientNameContainingAndIngredientType(String ingredientName, IngredientType ingredientType);
    Optional<Ingredient> findIngredientByIngredientNameAndIngredientCategory(String ingredientName, long ingredientCategoryId);
}
