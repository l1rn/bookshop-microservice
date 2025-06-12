package com.l1rn.user_service.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    public String accessToken;
    public String refreshToken;
}
