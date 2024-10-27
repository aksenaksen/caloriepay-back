package com.pknu.caloriepay.domain.user.dto.response;

import com.pknu.caloriepay.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;

    // Member -> MemberResponseDto 변환 메서드
    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
