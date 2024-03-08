package com.foody.food.repository;

import com.foody.food.entity.FoodSearch;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodSearchRepository extends JpaRepository<FoodSearch, Long>, FoodSearchJDBCRepository {

    Optional<FoodSearch> findFirstByName(String name);
    List<FoodSearch> findByNameContaining(String name);

    List<FoodSearch> findByNameStartingWith(String name);
    List<FoodSearch> findByNameStartingWith(String name, Pageable pageable);

    Optional<FoodSearch> findByName(String name);

    @Modifying
    @Query("UPDATE FoodSearch f SET f.score = f.score + :value WHERE f.name = :name")
    int updateScoreByName(@Param("name") String name, @Param("value") Double value);
}
