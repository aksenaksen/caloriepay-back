package com.pknu.caloriepay.domain.user.dao;

import com.pknu.caloriepay.domain.user.domain.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Long> {
    Optional<MemberHistory> findByUpdateDate(LocalDate updateDate);
}
