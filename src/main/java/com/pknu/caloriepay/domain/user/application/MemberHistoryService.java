package com.pknu.caloriepay.domain.user.application;

import com.pknu.caloriepay.domain.user.dao.MemberHistoryRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.domain.MemberHistory;
import com.pknu.caloriepay.domain.user.dto.response.WeightResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberHistoryService {
    private final MemberHistoryRepository memberHistoryRepository;

    public MemberHistoryService(MemberHistoryRepository memberHistoryRepository) {
        this.memberHistoryRepository = memberHistoryRepository;
    }

    @Transactional
    public void recordMemberHistory(Member member) {
        LocalDate today = LocalDate.now();
        Optional<MemberHistory> existingHistory = memberHistoryRepository.findByUpdateDate(today);

        if (existingHistory.isPresent()) {
            MemberHistory history = existingHistory.get();
            history.updateFields(member);
        } else {
            MemberHistory history = member.toHistory();
            memberHistoryRepository.save(history);
        }
    }

    @Transactional(readOnly = true)
    public List<WeightResponseDto> findMemberHistoryFor5Week(Long memberId){

        List<MemberHistory> histories = memberHistoryRepository.findByMemberIdOrderByUpdateDateDesc(memberId);
        if(histories.size()<=5){
            return histories.stream().map(WeightResponseDto::from)
                    .toList();
        }

        List<WeightResponseDto> resultHistories = new ArrayList<>();
        LocalDate currentWeekEnd = histories.get(0).getUpdateDate()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        resultHistories.add(WeightResponseDto.from(histories.get(0)));

        for (MemberHistory history : histories) {
            if (!history.getUpdateDate().isAfter(currentWeekEnd.minusWeeks(1))) {
                resultHistories.add(WeightResponseDto.from(history));
                currentWeekEnd = history.getUpdateDate().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            }

            if(resultHistories.size()==5){
                break;
            }
        }
        return resultHistories;
    }
}
