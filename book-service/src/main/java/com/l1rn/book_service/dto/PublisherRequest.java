package com.l1rn.book_service.dto;

import lombok.Data;

@Data
public class PublisherRequest {
    private String name;
    private String acronym;
    private String description;
    private int foundationYear;
}
