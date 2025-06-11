package com.l1rn.user_service.models.entity;

import com.l1rn.user_service.models.entity.user.UserEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

@Document(collection = "tokens")
public class Token implements Serializable {
    @Id
    private int id;

    private String token;

    private Instant expiryDate;

    @DBRef
    private UserEntity user;

    @DBRef
    private Device device;
}
