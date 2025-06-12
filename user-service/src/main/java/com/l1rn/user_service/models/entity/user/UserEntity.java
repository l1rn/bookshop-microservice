package com.l1rn.user_service.models.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.l1rn.user_service.models.entity.Device;
import com.l1rn.user_service.models.enums.ERole;
import com.l1rn.user_service.models.enums.EStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Nullable
    private String username;

    private String email;

    private String password;

    @OneToOne
    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToOne
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Device> devices = new ArrayList<>();

}
