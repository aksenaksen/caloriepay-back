package com.pknu.caloriepay.domain.score.application;

import com.pknu.caloriepay.domain.score.dao.CalorieScoreRepository;
import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import com.pknu.caloriepay.global.event.DailyCalorieSummaryEventDto;
import com.pknu.caloriepay.domain.score.dto.ResponseCalorieScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalorieScoreService {

    private final CalorieScoreRepository calorieScoreRepository;

    public ResponseCalorieScoreDto getCalorieScoreByUserIdAndDate(Long userId, LocalDate date){
        return calorieScoreRepository.findByUserIdAndDate(userId,date)
                .map(ResponseCalorieScoreDto::fromEntity)
                .orElse(null);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void calculateScore(DailyCalorieSummaryEventDto eventDto) {
        eventDto.getDailyCalorieChangeDtoList().forEach(dto -> {
            CalorieScore existingScore = calorieScoreRepository.findByUserIdAndDate(dto.getUserId(), LocalDate.now().minusDays(1))
                    .orElseThrow();

            CalorieScore newScore = CalorieScore.builder()
                    .userId(dto.getUserId())
                    .score(existingScore.getScore()) // 이전 점수 또는 계산된 값
                    .date(LocalDate.now())
                    .build();

            calorieScoreRepository.save(newScore); // 새로 생성된 CalorieScore 저장
        });
    }
}
