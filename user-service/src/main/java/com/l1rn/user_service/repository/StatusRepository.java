package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.user.Status;
import com.l1rn.user_service.models.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, String> {
    Status findStatusByName(EStatus name);
}
