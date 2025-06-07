package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.Role;
import com.l1rn.user_service.models.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role getRoleByName(ERole name);
}
