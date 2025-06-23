package com.l1rn.book_service.dto;

import com.l1rn.book_service.models.Publisher;
import lombok.Data;

@Data
public class BookRequest {
    private String isbn13;
    private String title;
    private String description;
    private int pages;
    private double price;
    private String publisherId;
}
