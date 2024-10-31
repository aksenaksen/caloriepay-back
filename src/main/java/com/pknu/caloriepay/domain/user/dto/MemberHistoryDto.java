package com.pknu.caloriepay.domain.user.dto;

import com.pknu.caloriepay.domain.user.domain.ActivityLevel;
import com.pknu.caloriepay.domain.user.domain.Goal;
import com.pknu.caloriepay.domain.user.domain.MemberHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class MemberHistoryDto {
    private Long memberId;
    private LocalDate updateDate;
    private String email;
    private String nickname;
    private Integer age;
    private Double weight;
    private Double height;
    private Goal goal;
    private ActivityLevel activityLevel;

    public static MemberHistoryDto from(MemberHistory memberHistory) {
        return MemberHistoryDto.builder()
                .memberId(memberHistory.getMemberId())
                .updateDate(memberHistory.getUpdateDate())
                .email(memberHistory.getEmail())
                .nickname(memberHistory.getNickname())
                .age(memberHistory.getAge())
                .weight(memberHistory.getHeight())
                .height(memberHistory.getHeight())
                .goal(memberHistory.getGoal())
                .activityLevel(memberHistory.getActivityLevel())
                .build();
    }

}
