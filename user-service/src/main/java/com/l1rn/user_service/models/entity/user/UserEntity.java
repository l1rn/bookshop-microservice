package com.l1rn.user_service.models.entity.user;

import com.l1rn.user_service.models.entity.Device;
import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Document(collection = "users")
public class UserEntity implements Serializable {
    @Id
    private int id;

    @Nullable
    private String username;

    private String email;

    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @DBRef
    private Set<Status> statuses = new HashSet<>();

    @DBRef
    private List<Device> devices = new ArrayList<>();

}
