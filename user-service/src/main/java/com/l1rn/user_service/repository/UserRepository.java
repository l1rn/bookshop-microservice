package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface    UserRepository extends MongoRepository<UserEntity, String> {
    Boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    @Query("{}")
    long countAllUsers();
}
