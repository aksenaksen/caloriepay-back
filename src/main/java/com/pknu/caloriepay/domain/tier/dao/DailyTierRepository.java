package com.pknu.caloriepay.domain.tier.dao;

import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyTierRepository extends JpaRepository<DailyTier,Long> {
    Optional<DailyTier> findByUserIdAndDate(Long userId, LocalDate date);
    List<DailyTier> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
