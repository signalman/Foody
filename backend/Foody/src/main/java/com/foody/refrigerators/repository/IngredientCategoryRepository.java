package com.foody.refrigerators.repository;

import com.foody.refrigerators.entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
}
