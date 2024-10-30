package com.pknu.caloriepay.domain.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_history_id")
    private Long id;

    @Column(name = "memberId")
    private Long memberId;

    @Column(name = "update_date")
    private LocalDate updateDate;

    private String nickname;

    private Integer age;

    private Double weight;

    private Double height;

    private Goal goal;

    private ActivityLevel activityLevel;




}
