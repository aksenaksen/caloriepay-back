package com.pknu.caloriepay.domain.user.application;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.user.dao.MemberHistoryRepository;
import com.pknu.caloriepay.domain.user.dao.MemberRankingRedisRepository;
import com.pknu.caloriepay.domain.user.dao.MemberRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.domain.MemberHistory;
import com.pknu.caloriepay.domain.user.dto.MemberDto;
import com.pknu.caloriepay.domain.user.dto.request.MemberUpdateRequest;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberHistoryService memberHistoryService;
    private final MemberRankingRedisRepository memberRankingRedisRepository;


    public MemberDto getMemberInfo(CurrentMemberInfo memberInfo){
        // Member 조회
        Member member = memberRepository.findById(memberInfo.memberId())
                .orElseThrow(() -> new CustomException(ResCode.USER_NOT_FOUND));
        return MemberDto.from(member);
    }

    @Transactional
    public MemberDto updateMemberInfo(CurrentMemberInfo memberInfo, MemberUpdateRequest updateRequest){
        Member member = memberRepository.findById(memberInfo.memberId())
                .orElseThrow(() -> new CustomException(ResCode.USER_NOT_FOUND));

        // 각 필드가 null이 아닌 경우에만 업데이트
        // 이메일 중복 검사
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(member.getEmail())) {
            memberRepository.findByEmail(updateRequest.getEmail())
                    .ifPresent(existingMember -> {
                        throw new CustomException(ResCode.DUPLICATE_USER_EMAIL);
                    });
            member.updateEmail(updateRequest.getEmail());
            memberHistoryService.recordMemberHistory(member);
        }
        // 닉네임 중복 검사
        if (updateRequest.getNickname() != null && !updateRequest.getNickname().equals(member.getNickname())) {
            memberRepository.findByNickname(updateRequest.getNickname())
                    .ifPresent(existingMember -> {
                        throw new CustomException(ResCode.DUPLICATE_USER_NICK);
                    });
            member.updateNickname(updateRequest.getNickname());
            memberHistoryService.recordMemberHistory(member);
        }

        // 전화번호 중복 검사
        if (updateRequest.getPhoneNumber() != null && !updateRequest.getPhoneNumber().equals(member.getPhoneNumber())) {
            memberRepository.findByPhoneNumber(updateRequest.getPhoneNumber())
                    .ifPresent(existingMember -> {
                        throw new CustomException(ResCode.DUPLICATE_USER_PHONE);
                    });
            member.updatePhoneNumber(updateRequest.getPhoneNumber());
            memberHistoryService.recordMemberHistory(member);
        }
        if (updateRequest.getAge() != null) member.getProfile().updateAge(updateRequest.getAge());memberHistoryService.recordMemberHistory(member);
        if (updateRequest.getHeight() != null) member.getProfile().updateHeight(updateRequest.getHeight());memberHistoryService.recordMemberHistory(member);
        if (updateRequest.getWeight() != null) member.getProfile().updateWeight(updateRequest.getWeight());memberHistoryService.recordMemberHistory(member);
        if (updateRequest.getTargetWeight() != null) member.getProfile().updateTargetWeight(updateRequest.getTargetWeight());memberHistoryService.recordMemberHistory(member);
        if (updateRequest.getGoal() != null) member.getProfile().updateGoal(updateRequest.getGoal());memberHistoryService.recordMemberHistory(member);
        if (updateRequest.getActivityLevel() != null) member.getProfile().updateActivityLevel(updateRequest.getActivityLevel());memberHistoryService.recordMemberHistory(member);
        if (updateRequest.getVisible() != null) member.getPreferences().updateVisible(updateRequest.getVisible());memberHistoryService.recordMemberHistory(member);
        return MemberDto.from(member);
    }

    @Transactional(readOnly = true)
    public RankInfo findRank(Long userId){
        RankInfo rank = memberRankingRedisRepository.findRank(userId);

        if(rank == null){
            int newRank = memberRepository.findRank(userId).intValue();
            return new RankInfo(userId,newRank,null);
        }

        return rank;
    }

    @Transactional(readOnly = true)
    public List<RankInfo> findRanks(){
        return memberRankingRedisRepository.findAll();
    }

}
