package com.foody.recommendednutrient.repository;

import com.foody.recommendednutrient.entity.RecommendedNutrient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedNutrientRepository extends JpaRepository<RecommendedNutrient, Long> {

    @Override
    Optional<RecommendedNutrient> findById(Long id);
}
