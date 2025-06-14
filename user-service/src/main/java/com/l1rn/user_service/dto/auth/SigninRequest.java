package com.l1rn.user_service.dto.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SigninRequest {
    public String email;
    public String password;
}
