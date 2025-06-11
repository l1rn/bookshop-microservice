package com.l1rn.user_service.models.entity.user;

import com.l1rn.user_service.models.enums.EStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "statuses")
public class Status {
    @Id
    private int id;

    private EStatus name;
}
