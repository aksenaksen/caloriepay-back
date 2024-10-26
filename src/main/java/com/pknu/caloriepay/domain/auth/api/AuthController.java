package com.pknu.caloriepay.domain.auth.api;

import com.pknu.caloriepay.domain.auth.application.AuthService;
import com.pknu.caloriepay.domain.auth.dto.request.LoginRequest;
import com.pknu.caloriepay.domain.auth.dto.response.JwtToken;
import com.pknu.caloriepay.global.dto.BaseRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseRes<JwtToken>> login(
            @RequestBody LoginRequest loginRequest
    ) throws Exception {
        return ResponseEntity.ok().body(BaseRes.success(authService.getAuthentication(loginRequest)));
    }
}
