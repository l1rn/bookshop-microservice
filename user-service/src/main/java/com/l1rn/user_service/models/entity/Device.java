package com.l1rn.user_service.models.entity;

import com.l1rn.user_service.models.entity.user.UserEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "devices")
public class Device implements Serializable {
    @Id
    private int id;
    private String fingerPrint;
    private String userAgent;
    private String ipAddress;

    @DBRef
    private UserEntity user;
}
