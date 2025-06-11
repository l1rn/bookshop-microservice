package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.user.Role;
import com.l1rn.user_service.models.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role getRoleByName(ERole name);
}
