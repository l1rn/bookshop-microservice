package com.l1rn.user_service.models.entity;

import com.l1rn.user_service.models.enums.ERole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Getter
@Setter
public class Role {
    @Id
    private int id;

    private ERole name;
}
