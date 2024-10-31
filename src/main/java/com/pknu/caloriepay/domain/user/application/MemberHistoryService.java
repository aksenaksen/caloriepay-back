package com.pknu.caloriepay.domain.user.application;

import com.pknu.caloriepay.domain.user.dao.MemberHistoryRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.domain.MemberHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
}
