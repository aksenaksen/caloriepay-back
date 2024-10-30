package com.pknu.caloriepay.domain.meal.dao;

import com.pknu.caloriepay.domain.meal.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByMemberIdAndMealTimeBetween(Long memberId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
