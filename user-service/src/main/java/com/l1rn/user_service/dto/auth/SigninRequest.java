package com.l1rn.user_service.dto.auth;

import lombok.Data;

@Data
public class SigninRequest {
    public String email;
    public String password;
}
