package com.l1rn.user_service.models.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.l1rn.user_service.models.entity.Device;
import com.l1rn.user_service.models.enums.ERole;
import com.l1rn.user_service.models.enums.EStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Device> devices = new ArrayList<>();

}
