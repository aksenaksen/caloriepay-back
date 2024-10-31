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

    private String email;

    private String nickname;

    private Integer age;

    private Double weight;

    private Double height;

    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    public void updateFields(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.age = member.getProfile().getAge();
        this.weight = member.getProfile().getWeight();
        this.height = member.getProfile().getHeight();
        this.goal = member.getProfile().getGoal();
        this.activityLevel = member.getProfile().getActivityLevel();
    }


}
