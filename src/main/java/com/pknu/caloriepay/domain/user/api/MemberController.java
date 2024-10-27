package com.pknu.caloriepay.domain.user.api;

import com.pknu.caloriepay.domain.user.application.MemberJoinService;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.dto.request.JoinRequestDto;
import com.pknu.caloriepay.domain.user.dto.response.MemberResponseDto;
import com.pknu.caloriepay.global.dto.BaseRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberJoinService memberJoinService;

    // 1 단계 회원가입 절차
    @PostMapping("/join")
    public ResponseEntity<BaseRes<MemberResponseDto>> join(@RequestBody JoinRequestDto joinRequestDto) {
        Member member = memberJoinService.joinMember(joinRequestDto);
        return ResponseEntity.ok().body(BaseRes.success(MemberResponseDto.from(member)));
    }


}
