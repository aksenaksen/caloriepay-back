package com.pknu.caloriepay.domain.tier.infrastructor;

import com.pknu.caloriepay.domain.tier.domain.QDailyTier;
import com.pknu.caloriepay.concept.Tier;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j

@RequiredArgsConstructor
public class CustomDailyTierRepositoryImpl implements CustomDailyTierRepository{

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public Map<Tier, Long> countByTierGroupByUserId(Long userId, LocalDate start, LocalDate end) {
        QDailyTier qDailyTier = QDailyTier.dailyTier;

        return jpqlQueryFactory
                .select(qDailyTier.tier, qDailyTier.count())
                .from(qDailyTier)
                .where(qDailyTier.userId.eq(userId)
                        .and(qDailyTier.date.between(start, end)))
                .groupBy(qDailyTier.tier)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(qDailyTier.tier),
                        tuple -> tuple.get(qDailyTier.count())
                ));
    }

}
