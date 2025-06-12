package com.l1rn.user_service.models.entity;

import com.l1rn.user_service.models.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;

@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String token;

    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "device", nullable = false)
    private Device device;
}
