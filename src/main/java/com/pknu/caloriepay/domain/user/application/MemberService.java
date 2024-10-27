package com.pknu.caloriepay.domain.user.application;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.user.dao.MemberRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.dto.MemberDto;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto getMemberInfo(CurrentMemberInfo memberInfo){
        // Member 조회
        Member member = memberRepository.findById(memberInfo.memberId())
                .orElseThrow(() -> new CustomException(ResCode.USER_NOT_FOUND));
        return MemberDto.from(member);
    }
}
