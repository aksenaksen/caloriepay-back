package com.pknu.caloriepay.domain.recommandcalorie.infrastructor;

import com.pknu.caloriepay.domain.recommandcalorie.domain.RecommandCalorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommandCalorieHistoryRepository extends JpaRepository<RecommandCalorie, Long> {
    Optional<RecommandCalorie> findByUserId(Long userId);
}
