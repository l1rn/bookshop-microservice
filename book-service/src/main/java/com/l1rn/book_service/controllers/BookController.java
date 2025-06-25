package com.l1rn.book_service.controllers;

import com.l1rn.book_service.dto.BookRequest;
import com.l1rn.book_service.models.Book;
import com.l1rn.book_service.services.BookService;
import com.l1rn.book_service.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private final BookService bookService;
    @Autowired
    private final PublisherService publisherService;

    @PostMapping("/books/add")
    public ResponseEntity<Book> addBook(@RequestBody BookRequest book){
        Book addedBook = bookService.addBook(book);
        publisherService.addBookToPublisher(book.getPublisherId(), addedBook.getId());
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

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable String id,
                                            @RequestBody BookRequest bookRequest){
        return ResponseEntity.ok(bookService.update(id, bookRequest));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable String id){
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}