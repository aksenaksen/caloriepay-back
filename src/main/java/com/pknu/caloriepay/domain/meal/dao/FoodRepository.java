package com.pknu.caloriepay.domain.meal.dao;

import com.pknu.caloriepay.domain.meal.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
