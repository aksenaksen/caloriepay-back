package com.pknu.caloriepay.domain.meal.dao;

import com.pknu.caloriepay.domain.meal.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
