package com.pknu.caloriepay.domain.score.application;

import com.pknu.caloriepay.domain.score.application.out.CalorieScoreHistoryResponse;
import com.pknu.caloriepay.domain.score.dao.CalorieScoreHistoryRepository;
import com.pknu.caloriepay.domain.score.domain.CalorieScoreHistory;
import com.pknu.caloriepay.domain.user.dao.MemberRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import com.pknu.caloriepay.domain.score.event.DailyCalorieSummaryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalorieScoreHistoryService {

    private final CalorieScoreHistoryRepository calorieScoreRepository;
    private final CalorieScoreHistoryFinder calorieScoreHistoryFinder;
    private final MemberRepository memberRepository;

//    public ResponseCalorieScoreDto getCalorieScoreByUserIdAndDate(Long userId){
//        Member member = memberRepository.findById(userId).orElseThrow(() ->new CustomException(ResCode.USER_NOT_FOUND));
//
//        return calorieScoreRepository.findTopByUserIdOrderByDateDesc(userId)
//                .map((calorieScore) ->ResponseCalorieScoreDto.fromEntity(calorieScore,member))
//                .orElse(null);
//    }

    @Transactional(readOnly = true)
    public List<CalorieScoreHistoryResponse> findCalorieScoreHistoryBetweenMonth(Long userId, Integer offset) {

        Member member = memberRepository.findById(userId).orElseThrow(() ->new CustomException(ResCode.USER_NOT_FOUND));
        LocalDate currentDate = LocalDate.now();

        return calorieScoreHistoryFinder.getCalorieScoreChangeForMonth(userId,offset,currentDate).stream()
                .map(score -> CalorieScoreHistoryResponse.of(
                        score,
                        member.getName()
                ))
                .toList();
    }

//    @Transactional
//    public void refreshCalorieScoreByUserId(Long userId) {
//        calorieScoreRepository.findByUserIdAndDate(userId, LocalDate.now())
//                .ifPresentOrElse(
//                        calorieScore -> {
//                        },
//                        () -> {
//                            CalorieScore latestScore = calorieScoreRepository.findTopByUserIdOrderByDateDesc(userId)
//                                    .orElseThrow(() -> new CustomException(ResCode.SCORE_NOT_FOUND));
//                            calorieScoreRepository.save(
//                                    CalorieScore.builder()
//                                            .userId(latestScore.getUserId())
//                                            .score(latestScore.getScore())
//                                            .date(LocalDate.now())
//                                            .build()
//                            );
//                        }
//                );
//    }
//  배치 도입 이후 필요없음.
    @Async("threadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void calculateScore(DailyCalorieSummaryEvent eventDto) {
        eventDto.getDailyCalorieChangeDtoList().forEach(dto -> {
            CalorieScoreHistory existingScore = calorieScoreRepository.findByUserIdAndDate(dto.getUserId(), LocalDate.now().minusDays(1))
                    .orElseThrow(() -> new CustomException(ResCode.SCORE_NOT_FOUND));

            CalorieScoreHistory newScore = CalorieScoreHistory.builder()
                    .userId(dto.getUserId())
                    .score(existingScore.getScore()) // 이전 점수 또는 계산된 값
                    .date(LocalDate.now())
                    .build();

            calorieScoreRepository.save(newScore); // 새로 생성된 CalorieScore 저장
        });
    }
}
