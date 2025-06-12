package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.user.Role;
import com.l1rn.user_service.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role getRoleByName(ERole name);
}
