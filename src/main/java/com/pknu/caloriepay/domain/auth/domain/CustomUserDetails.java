package com.pknu.caloriepay.domain.auth.domain;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long memberId;
    private final String name;
    private final String phoneNumber;
    private final String nickname;
    private final String email;
    private final String password;

    public CustomUserDetails(Long memberId, String email, String password, String phoneNumber, String nickname, String name) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // 빈 리스트 반환
    }

    public CurrentMemberInfo getCurrentUser() { return new CurrentMemberInfo(memberId);}

    @Override
    public String getPassword() {
        return password;  // 유저의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return email;  // 유저의 이메일 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정이 만료되지 않았는지 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 계정이 잠겨있지 않은지 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 비밀번호가 만료되지 않았는지 여부
    }

    @Override
    public boolean isEnabled() {
        return true;  // 계정이 활성화되었는지 여부
    }
}
