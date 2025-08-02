package com.pknu.caloriepay.domain.user.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.user.application.MemberHistoryService;
import com.pknu.caloriepay.domain.user.application.MemberJoinService;
import com.pknu.caloriepay.domain.user.application.MemberService;
import com.pknu.caloriepay.domain.user.application.RankInfo;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.dto.MemberDto;
import com.pknu.caloriepay.domain.user.dto.ProfileDto;
import com.pknu.caloriepay.domain.user.dto.request.JoinRequestDto;
import com.pknu.caloriepay.domain.user.dto.request.MemberUpdateRequest;
import com.pknu.caloriepay.domain.user.dto.response.JoinResponseDto;
import com.pknu.caloriepay.domain.user.dto.response.WeightResponseDto;
import com.pknu.caloriepay.global.dto.BaseRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberJoinService memberJoinService;
    private final MemberService memberService;
    private final MemberHistoryService memberHistoryService;

    @GetMapping("")
    public ResponseEntity<BaseRes<MemberDto>> getMemberInfo(@AuthenticationPrincipal CurrentMemberInfo memberInfo) {
        MemberDto res = memberService.getMemberInfo(memberInfo);
        return ResponseEntity.ok().body(BaseRes.success(res));
    }

    @PatchMapping("")
    public ResponseEntity<BaseRes<MemberDto>> updateMemberInfo(
            @RequestBody MemberUpdateRequest updateRequest,
            @AuthenticationPrincipal CurrentMemberInfo memberInfo) {
        MemberDto res = memberService.updateMemberInfo(memberInfo, updateRequest);
        return ResponseEntity.ok().body(BaseRes.success(res));
    }

    @GetMapping("/weight")
    public ResponseEntity<BaseRes<List<WeightResponseDto>>> getMemberHistory(@AuthenticationPrincipal CurrentMemberInfo memberInfo){

        List<WeightResponseDto> res = memberHistoryService.findMemberHistoryFor5Week(memberInfo.memberId());
        return ResponseEntity.ok(BaseRes.success(res));
    }

    // 1 단계 회원가입 절차
    @PostMapping("/join")
    public ResponseEntity<BaseRes<JoinResponseDto>> join(@RequestBody JoinRequestDto joinRequestDto) {
        Member member = memberJoinService.joinMember(joinRequestDto);
        return ResponseEntity.ok().body(BaseRes.success(JoinResponseDto.from(member)));
    }

    @PostMapping("/profile")
    public ResponseEntity<BaseRes<ProfileDto>> joinProfile(
            @RequestBody ProfileDto profileDto,
            @AuthenticationPrincipal CurrentMemberInfo memberInfo) {
        ProfileDto res = memberJoinService.registerProfile(profileDto, memberInfo);
        return ResponseEntity.ok().body(BaseRes.success(res));
    }

    @GetMapping("/rank")
    public ResponseEntity<BaseRes<List<RankInfo>>> getCalorieScoreRanking(){

        List<RankInfo> res = memberService.findRanks();
        return ResponseEntity.ok(BaseRes.success(res));
    }

    @GetMapping("/rank/member")
    public ResponseEntity<BaseRes<RankInfo>> getCalorieScoreRankingMember(@AuthenticationPrincipal CurrentMemberInfo memberInfo){

        RankInfo res = memberService.findRank(memberInfo.memberId());
        return ResponseEntity.ok(BaseRes.success(res));
    }
}
