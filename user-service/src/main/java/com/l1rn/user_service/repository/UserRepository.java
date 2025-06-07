package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String username);
    @Query("{}")
    long countAllUsers();
}
