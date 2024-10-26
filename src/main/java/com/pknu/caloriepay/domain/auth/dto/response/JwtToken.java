package com.pknu.caloriepay.domain.auth.dto.response;

public record JwtToken(String grantType,String accessToken,String refreshToken) {
}
