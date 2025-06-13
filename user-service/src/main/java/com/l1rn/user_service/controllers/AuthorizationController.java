package com.l1rn.user_service.controllers;

import com.l1rn.user_service.dto.auth.SignupRequest;
import com.l1rn.user_service.services.AuthorizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    @Autowired
    private final AuthorizationService authorizationService;

    @Value("${jwt.token.access.expiration}")
    private long cookiesAccessAge;
    @Value("${jwt.token.refresh.expiration}")
    private long cookiesRefreshAge;

    private ResponseEntity<?> signup(@RequestBody SignupRequest request){
        authorizationService.signup(request);
        return ResponseEntity.ok("Пользователь создан!");
    }

    private Re

    private void setAuthCookies(HttpServletResponse response, String accessToken, String refreshToken){
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookiesAccessAge / 1000)
                .sameSite("Lax")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookiesRefreshAge / 1000)
                .sameSite("Lax")
                .build();

        response.setHeader("Set-Cookie", accessToken);
        response.setHeader("Set-Cookie", refreshToken);
    }

    private void clearAuthCookies(HttpServletResponse response, String accessToken, String refreshToken){
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .maxAge(0)
                .path("/")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("accessToken", accessToken)
                .maxAge(0)
                .path("/")
                .build();

        response.setHeader("Set-Cookie", accessToken);
        response.setHeader("Set-Cookie", refreshToken);
    }
}