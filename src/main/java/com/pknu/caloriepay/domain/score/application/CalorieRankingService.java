package com.pknu.caloriepay.domain.score.application;


import com.pknu.caloriepay.domain.score.dao.CalorieScoreRepository;
import com.pknu.caloriepay.domain.score.domain.CalorieScore;
import com.pknu.caloriepay.domain.score.dto.ResponseCalorieScoreRankingDto;
import com.pknu.caloriepay.domain.user.dao.MemberRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CalorieRankingService {

    private final CalorieScoreRepository calorieScoreRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<ResponseCalorieScoreRankingDto> getLatestScoreRanking() {
        List<CalorieScore> calorieScores = calorieScoreRepository.findLatestScoresByUserOrderByScoreDesc(PageRequest.of(0, 100));
//      100등까지의 리스트만 뽑아옴
        return IntStream.rangeClosed(1, calorieScores.size())
                .mapToObj(i -> {
                    CalorieScore calorieScore = calorieScores.get(i - 1);
                    Member member = memberRepository.findById(calorieScore.getUserId()).orElseThrow();
                    return ResponseCalorieScoreRankingDto.of(calorieScore, member, (long) i);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ResponseCalorieScoreRankingDto findUserRankingByUserId(Long userId){

        Map<String , Object> calorieScoreMap = calorieScoreRepository.findUserRankingByUserId(userId);
        Member member = memberRepository.findById(userId).orElseThrow();

        CalorieScore calorieScore= (CalorieScore) calorieScoreMap.get("calorieScore");
        Long ranking = (long) calorieScoreMap.get("ranking");

        return ResponseCalorieScoreRankingDto.of(calorieScore,member, ranking);
    }


}
