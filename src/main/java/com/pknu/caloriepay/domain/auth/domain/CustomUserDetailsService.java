package com.pknu.caloriepay.domain.auth.domain;

import com.pknu.caloriepay.domain.user.dao.MemberCredentialsRepository;
import com.pknu.caloriepay.domain.user.dao.MemberRepository;
import com.pknu.caloriepay.domain.user.domain.Member;
import com.pknu.caloriepay.domain.user.domain.MemberCredentials;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberCredentialsRepository memberCredentialsRepository;

    public CustomUserDetailsService(MemberRepository memberRepository, MemberCredentialsRepository memberCredentialsRepository) {
        this.memberRepository = memberRepository;
        this.memberCredentialsRepository = memberCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws CustomException {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByEmail(email))
                .orElseThrow(() -> new CustomException(ResCode.USER_NOT_FOUND));

        Member currentMember = member.get();
        MemberCredentials memberCredentials = memberCredentialsRepository.findById(currentMember.getId())
                .orElseThrow(()-> new NoSuchElementException("member: " + currentMember.getId() + " not found password"));

        return new CustomUserDetails(currentMember.getId(), currentMember.getEmail(), memberCredentials.getEncodedPassword(), currentMember.getPhoneNumber(), currentMember.getNickname(), currentMember.getName());

    }

    public UserDetails loadUserById(Long memberId) throws CustomException {
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(memberId))
                .orElseThrow(() -> new CustomException(ResCode.USER_NOT_FOUND));

        Member currentMember = member.get();
        MemberCredentials memberCredentials = memberCredentialsRepository.findById(currentMember.getId())
                .orElseThrow(()-> new NoSuchElementException("member: " + currentMember.getId() + " not found password"));

        return new CustomUserDetails(currentMember.getId(), currentMember.getEmail(), memberCredentials.getEncodedPassword(), currentMember.getPhoneNumber(), currentMember.getNickname(), currentMember.getName());
    }




}
