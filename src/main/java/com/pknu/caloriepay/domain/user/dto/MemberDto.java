package com.pknu.caloriepay.domain.user.dto;

import com.pknu.caloriepay.domain.user.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Builder
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String phoneNumber;
    private Profile profile;
    private Preferences preferences;

    // 정적 팩토리 메서드 from
    public static MemberDto from(Member member) {
        log.info(member.getProfile().toString());
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .profile(member.getProfile())
                .preferences(member.getPreferences())
                .build();
    }

}