package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.Device;
import com.l1rn.user_service.models.entity.Token;
import com.l1rn.user_service.models.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    void deleteByUserAndDevice(UserEntity user, Device device);
}
