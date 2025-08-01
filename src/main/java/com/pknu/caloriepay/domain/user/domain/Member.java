package com.pknu.caloriepay.domain.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String nickname;

    private Integer score;

    @Embedded
    private Profile profile;

    @Embedded
    private Preferences preferences;

    // Member 엔티티의 생성 메서드
    public static Member createMember(String name, String email, String phoneNumber, String nickname, Profile profile, Preferences preferences,Integer score) {
        return Member.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .profile(profile)
                .preferences(preferences)
                .score(score)
                .build();
    }

    // MemberHistory 엔티티 생성 메서드
    public MemberHistory toHistory() {
        return MemberHistory.builder()
                .memberId(this.id)
                .updateDate(LocalDate.now()) // 현재 날짜를 업데이트 날짜로 설정
                .email(this.email)
                .nickname(this.nickname)
                .age(this.profile.getAge())
                .weight(this.profile.getWeight())
                .height(this.profile.getHeight())
                .goal(this.profile.getGoal())
                .activityLevel(this.profile.getActivityLevel())
                .build();
    }

    // profile update
    public void updateProfile(Profile profile) {
        this.profile = profile;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }



}
