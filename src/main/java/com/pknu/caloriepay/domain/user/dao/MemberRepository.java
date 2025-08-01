package com.pknu.caloriepay.domain.user.dao;

import com.pknu.caloriepay.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);// 이메일 중복체크

    Optional<Member> findByNickname(String nickname); // 닉네임 중복 체크

    Optional<Member> findByPhoneNumber(String phoneNumber);

    @Query(value = """
    SELECT ranking FROM
    (
        SELECT member_id, RANK() OVER(ORDER BY score DESC) as ranking
        FROM member
    ) as ranked_table
    WHERE member_id = :userId
    """, nativeQuery = true)
    Long findRank(Long userId);
}
