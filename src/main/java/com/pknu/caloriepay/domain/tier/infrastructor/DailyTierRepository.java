package com.pknu.caloriepay.domain.tier.infrastructor;

import com.pknu.caloriepay.domain.tier.domain.DailyTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyTierRepository extends JpaRepository<DailyTier,Long>,CustomDailyTierRepository {
    Optional<DailyTier> findByUserIdAndDate(Long userId, LocalDate date);
    List<DailyTier> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
