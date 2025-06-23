package com.l1rn.book_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@Document(collection = "publishers")
public class Publisher {
    private String name;
    private String acronym;
    private String description;
    private int foundationYear;
    @DBRef
    private List<Book> books;
}
