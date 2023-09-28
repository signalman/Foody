package com.foody.mealplan.repository;

import com.foody.mealplan.entity.MealPlan;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByDateTimeBetweenAndMemberId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long memberId);
}
