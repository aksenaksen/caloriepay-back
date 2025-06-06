package com.pknu.caloriepay.domain.tier.domain;

import com.pknu.caloriepay.global.event.DailyCalorieSummaryEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.util.List;

public class DailyCalorieSummaryEventListener {

    @PersistenceContext
    private EntityManager em;

    @Async("threadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void calculateTierAll(DailyCalorieSummaryEvent eventDto) {
//      요일 정산시 어제날짜로 티어가 정산됨.
//        List<DailyTier> tierList = eventDto.getDailyCalorieChangeDtoList().forEach(calorieChange -> {
//            Long userId = calorieChange.getUserId();
//
////            dailyTierRepository.save(DailyTier.builder()
////                    .userId(userId)
////                    .tier(Tier.calculateDailyTier(calorieChange.getRemainCalorie())) // 티어 계산
////                    .date(LocalDate.now().minusDays(1)) // 어제 날짜
////                    .build()); // DailyTier 저장
//        });

        List<DailyTier> tierList = eventDto.getDailyCalorieChangeDtoList()
                .stream()
                .map((dto) ->
                        DailyTier.of(dto.getUserId(),Tier.calculateDailyTier(dto.getRemainCalorie()), LocalDate.now().minusDays(1))
                ).toList();

        Session session = em.unwrap(Session.class);
        session.setJdbcBatchSize(100);

        for(int i=0; i<tierList.size(); i++){
            session.persist(tierList.get(i));

            if(i%100 == 0 || i == tierList.size()-1){
                session.flush();
                session.clear();
            }
        }

    }
}
