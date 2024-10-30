package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import com.pknu.caloriepay.domain.score.domain.QCalorieScore;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.*;

@RequiredArgsConstructor
public class CustomCalorieScoreRepositoryImpl implements CustomCalorieScoreRepository{

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<CalorieScore> findLatestScoresByUserOrderByScoreDesc(Pageable pageable) {
        QCalorieScore qs = QCalorieScore.calorieScore;

        return jpqlQueryFactory
                .selectFrom(qs)
                .where(qs.date.in(
                        JPAExpressions.select(qs.date.max())
                                .from(qs)
                                .groupBy(qs.userId)
                ))
                .orderBy(qs.score.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Optional<CalorieScore> findLatestScoreByUserIdAndYearAndMonth(Long userId, int year, int month) {
        QCalorieScore qs = QCalorieScore.calorieScore;

        CalorieScore result = jpqlQueryFactory
                .selectFrom(qs)
                .where(qs.userId.eq(userId)
                        .and(qs.date.year().eq(year))
                        .and(qs.date.month().eq(month)))
                .orderBy(qs.score.desc())
                .fetchFirst();

        return Optional.ofNullable(result);
    }
    @Override
    public Map<String, Object> findUserRankingByUserId(Long userId) {
        QCalorieScore qs = QCalorieScore.calorieScore;

        CalorieScore latestCalorieScore = jpqlQueryFactory
                .selectFrom(qs)
                .where(qs.userId.eq(userId)
                        .and(qs.date.eq(
                                JPAExpressions.select(qs.date.max())
                                        .from(qs)
                                        .where(qs.userId.eq(userId))
                        )))
                .fetchOne();

        // 해당 점수보다 높은 점수의 개수 세기
        Long ranking = jpqlQueryFactory
                .select(qs.count())
                .from(qs)
                .where(qs.score.gt(latestCalorieScore.getScore()))
                .fetchOne();

        long finalRanking = ranking != null ? ranking + 1 : 1L; // 기본값 1 반환

        Map<String, Object> result = new HashMap<>();
        result.put("calorieScore", latestCalorieScore);
        result.put("ranking", finalRanking);

        return result;
    }
}
