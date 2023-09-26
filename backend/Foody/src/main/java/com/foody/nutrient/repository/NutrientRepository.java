package com.foody.nutrient.repository;

import com.foody.nutrient.entity.Nutrient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepository extends JpaRepository<Nutrient, Long> {

    @Override
    Optional<Nutrient> findById(Long id);
}
