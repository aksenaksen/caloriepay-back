package com.pknu.caloriepay.domain.user.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.user.application.MemberJoinService;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.dto.JoinRequestDto;
import com.pknu.caloriepay.domain.user.dto.MemberResponseDto;
import com.pknu.caloriepay.global.dto.BaseRes;
import com.pknu.caloriepay.global.enums.ResCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberJoinService memberJoinService;

    // 1 단계 회원가입 절차
    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        Member member = memberJoinService.joinMember(joinRequestDto);
        return ResponseEntity.ok(MemberResponseDto.from(member));
    }


}
