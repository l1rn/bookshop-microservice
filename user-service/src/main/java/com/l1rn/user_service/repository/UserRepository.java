package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface    UserRepository extends JpaRepository<UserEntity, String> {
    Boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
