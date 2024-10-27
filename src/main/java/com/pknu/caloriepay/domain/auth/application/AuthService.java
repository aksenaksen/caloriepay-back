package com.pknu.caloriepay.domain.auth.application;

import com.pknu.caloriepay.domain.auth.JwtTokenProvider;
import com.pknu.caloriepay.domain.auth.domain.CustomUserDetails;
import com.pknu.caloriepay.domain.auth.domain.CustomUserDetailsService;
import com.pknu.caloriepay.domain.auth.dto.request.LoginRequest;
import com.pknu.caloriepay.domain.auth.dto.response.JwtToken;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtToken getAuthentication(LoginRequest request) throws CustomException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (AuthenticationException e) {
            throw new CustomException(ResCode.USER_NOT_FOUND);
        }

        final CustomUserDetails userDetails = (CustomUserDetails)customUserDetailsService.loadUserByUsername(
                request.email());
        final JwtToken jwt = jwtTokenProvider.generateToken(userDetails);
        return jwt;
    }
}
