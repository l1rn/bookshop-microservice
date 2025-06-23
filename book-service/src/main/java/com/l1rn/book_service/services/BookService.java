package com.l1rn.book_service.services;

import com.l1rn.book_service.dto.BookRequest;
import com.l1rn.book_service.models.Book;
import com.l1rn.book_service.repositories.BookRepository;
import com.l1rn.book_service.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final PublisherRepository publisherRepository;

    public Book addBook(BookRequest bookRequest){
        Book book = Book.builder()
                .isbn13(bookRequest.getIsbn13())
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .pages(bookRequest.getPages())
                .price(bookRequest.getPrice())
                .publisher(publisherRepository.findById(bookRequest.getPublisherId())
                        .orElseThrow(() -> new RuntimeException("Not found this publisher")))
                .build();
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getById(String id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found this book by id"));
    }

    public void deleteById(String id){
        bookRepository.deleteById(id);
    }
}
