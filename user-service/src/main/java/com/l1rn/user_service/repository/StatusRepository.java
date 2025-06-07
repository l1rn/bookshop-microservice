package com.l1rn.user_service.repository;

import com.l1rn.user_service.models.entity.Status;
import com.l1rn.user_service.models.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusRepository extends MongoRepository<Status, String> {
    Status findStatusByName(EStatus name);
}
