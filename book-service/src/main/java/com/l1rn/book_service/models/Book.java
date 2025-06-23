package com.l1rn.book_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "books")
public class Book {
    private String isbn13;
    private String title;
    private String description;
    private int pages;
    private double price;
    private Publisher publisher;
}
