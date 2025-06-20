package com.l1rn.user_service.controllers;

import com.l1rn.user_service.dto.auth.JwtResponse;
import com.l1rn.user_service.dto.auth.SigninRequest;
import com.l1rn.user_service.dto.auth.SignupRequest;
import com.l1rn.user_service.services.AuthorizationService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    @Autowired
    private final AuthorizationService authorizationService;

    @Value("${jwt.token.access.expiration}")
    private long cookiesAccessAge;
    @Value("${jwt.token.refresh.expiration}")
    private long cookiesRefreshAge;

    @PostMapping("/register")
    private ResponseEntity<?> signup(@RequestBody SignupRequest request){
        authorizationService.signup(request);
        return ResponseEntity.ok("Пользователь создан!");
    }

    @PostMapping("/login")
    private ResponseEntity<?> signin(@RequestBody
                                     SigninRequest signinRequest,
                                     HttpServletRequest request,
                                     HttpServletResponse response){
        JwtResponse authResponse = authorizationService.signin(signinRequest, request);

        response.setHeader("X-Token-Expires", String.valueOf(cookiesAccessAge));

        setAuthCookies(response, authResponse.getAccessToken(), authResponse.getRefreshToken());
        return ResponseEntity.ok("Authorized");
    }

    @PostMapping("/logout")
    private ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(c -> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Token is empty!"));

        authorizationService.deleteToken(refreshToken);
        clearAuthCookies(response, refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(c -> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Token is empty!"));

        JwtResponse newTokens = authorizationService.refreshToken(refreshToken);
        response.addHeader("X-Token-Expires", String.valueOf(cookiesAccessAge));

        setAuthCookies(response, newTokens.getAccessToken(), newTokens.getRefreshToken());
        return ResponseEntity.ok().build();
    }

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

    private void clearAuthCookies(HttpServletResponse response, String refreshToken){
        ResponseCookie refreshCookie = ResponseCookie.from("accessToken", refreshToken)
                .maxAge(0)
                .path("/")
                .build();

        response.setHeader("Set-Cookie", refreshToken);
    }
}