package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.Device;
import com.l1rn.user_service.models.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
     Optional<Device> findByFingerprintAndUser(String fingerprint, UserEntity user);
}
