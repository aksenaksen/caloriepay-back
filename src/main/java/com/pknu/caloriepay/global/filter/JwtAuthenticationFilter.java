package com.pknu.caloriepay.global.filter;

import com.pknu.caloriepay.domain.auth.JwtTokenProvider;
import com.pknu.caloriepay.domain.auth.domain.CustomUserDetailsService;
import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private final CustomUserDetailsService userDetailsService;
//    private final JwtTokenProvider jwtTokenProvider;

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 요청에서 Authorization 헤더를 추출하여 JWT 토큰 가져오기
//        final String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
//        System.out.println("filter:"+authorizationHeader);
//        CurrentMemberInfo memberInfo = null;
//        String jwt = null;
//
//        // "Bearer "로 시작하는 헤더에서 토큰 추출
//        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            jwt = authorizationHeader.substring(7);
//            memberInfo = jwtTokenProvider.extractUserInfo(jwt);
//        }
//
//        // 사용자가 인증되지 않았고, 토큰이 유효할 경우 인증 설정
//        if (memberInfo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserById(memberInfo.memberId());
//
//            // 토큰 유효성 검증
//            if (jwtTokenProvider.validateToken(jwt)) {
//                // 인증 객체 생성 및 설정
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(memberInfo, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // SecurityContext에 인증 설정
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//
//        // 다음 필터로 요청을 전달
//        chain.doFilter(request, response);
    }


}
