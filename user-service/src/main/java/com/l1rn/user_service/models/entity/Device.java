package com.l1rn.user_service.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.l1rn.user_service.models.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "devices")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fingerPrint;
    private String userAgent;
    private String ipAddress;
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private UserEntity user;
}
