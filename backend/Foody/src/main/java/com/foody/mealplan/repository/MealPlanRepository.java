package com.foody.mealplan.repository;

import com.foody.mealplan.entity.MealPlan;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    Optional<MealPlan> findByDateAndMemberId(LocalDate date, Long id);

    @Query("SELECT m.date FROM MealPlan m WHERE m.member.id = :memberId AND m.date > :startDate")
    List<LocalDate> findDatesByMemberIdAndDateAfter(@Param("memberId") Long id, @Param("startDate") LocalDate startDate);
}
