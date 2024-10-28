package com.pknu.caloriepay.domain.tier.dao;

import com.pknu.caloriepay.domain.tier.domain.MonthlyTier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MonthlyTierRepository extends JpaRepository<MonthlyTier, Long> {

    Optional<MonthlyTier> findByUserIdAndDate(Long userId, LocalDate date);
}
