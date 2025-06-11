package com.l1rn.user_service.dto.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignupDTO {
    private int id;
    private String email;
    private String password;
}
