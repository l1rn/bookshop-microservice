package com.l1rn.book_service.controllers;

import com.l1rn.book_service.dto.BookRequest;
import com.l1rn.book_service.models.Book;
import com.l1rn.book_service.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/books/add")
    public ResponseEntity<Book> addBook(@RequestBody BookRequest book){
        Book addedBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id){
        return ResponseEntity.ok(bookService.getById(id));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable String id){
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}