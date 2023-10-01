package com.foody.food.repository;

import com.foody.food.entity.FoodSearch;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodSearchRepository extends JpaRepository<FoodSearch, Long>, FoodSearchJDBCRepository {

    Optional<FoodSearch> findByName(String name);
}
