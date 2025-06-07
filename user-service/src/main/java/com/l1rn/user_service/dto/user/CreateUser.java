package com.l1rn.user_service.dto.user;

import lombok.Data;

@Data
public class CreateUser {
    private int id;
    private String username;
    private String email;
    private String password;
}