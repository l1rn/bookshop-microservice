package com.l1rn.user_service.services;

import com.l1rn.user_service.dto.auth.SignupDTO;
import com.l1rn.user_service.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    @Autowired
    private final JwtUtils jwtUtils;

    public void signup(SignupDTO request){

    }
}
