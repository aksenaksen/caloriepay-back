package com.pknu.caloriepay.domain.score.dao;

import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;
import com.pknu.caloriepay.domain.score.domain.QCalorieScore;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
public class QueryDslCalorieScoreHistoryRepositoryImpl implements CustomCalorieScoreHistoryRepository {

    private final JPQLQueryFactory jpqlQueryFactory;

    @PersistenceContext
    private EntityManager entityManager;


//    @Override
//    public List<CalorieScore> findLatestScoresByUserOrderByScoreDesc(Pageable pageable) {
//        QCalorieScore qs = QCalorieScore.calorieScore;
//        QCalorieScore subQs = new QCalorieScore("subQs");
//
//        return jpqlQueryFactory
//                .selectFrom(qs)
//                .where(qs.date.eq(
//                        JPAExpressions.select(subQs.date.max())  // 최신 날짜
//                                .from(subQs)
//                                .where(subQs.userId.eq(qs.userId)) // 같은 userId에 대해 최신 날짜
//                ))
//                .orderBy(qs.score.desc())  // 점수 내림차순 정렬
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//    }



    public List<CalorieScoreHistory> findLatestScoresByUserOrderByScoreDesc(Pageable pageable) {
        String sql = "SELECT * FROM ( " +
                "SELECT cs.*, ROW_NUMBER() OVER (PARTITION BY cs.user_id ORDER BY cs.date DESC) AS row_num " +
                "FROM calorie_score cs) AS sub " +
                "WHERE sub.row_num = 1 " +
                "ORDER BY sub.score DESC";

        Query query = entityManager.createNativeQuery(sql, CalorieScoreHistory.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    @Override
    public Optional<CalorieScoreHistory> findLatestScoreByUserIdAndYearAndMonth(Long userId, int year, int month) {
        QCalorieScore qs = QCalorieScore.calorieScore;

        CalorieScoreHistory result = jpqlQueryFactory
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

        CalorieScoreHistory latestCalorieScore = jpqlQueryFactory
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
    @Override
    public Optional<CalorieScoreHistory> findHighScoreOfMonthByUserId(Long userId, LocalDate start, LocalDate end){
        QCalorieScore qs = QCalorieScore.calorieScore;

        return Optional.ofNullable(
                jpqlQueryFactory
                        .selectFrom(qs)
                        .where(qs.userId.eq(userId)
                                .and(qs.date.between(start, end))
                        )
                        .orderBy(qs.score.desc())
                        .limit(1)
                        .fetchOne()
        );

    }
}
