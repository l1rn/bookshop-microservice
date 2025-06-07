package com.l1rn.user_service.models.entity;

import com.mongodb.lang.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nullable
    private String username;

    private String email;

    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @DBRef
    private Set<Status> statuses = new HashSet<>();
}
