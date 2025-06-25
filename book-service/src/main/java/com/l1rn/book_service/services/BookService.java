package com.l1rn.book_service.services;

import com.l1rn.book_service.dto.BookRequest;
import com.l1rn.book_service.models.Book;
import com.l1rn.book_service.models.Publisher;
import com.l1rn.book_service.repositories.BookRepository;
import com.l1rn.book_service.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final PublisherRepository publisherRepository;

    public Book addBook(BookRequest bookRequest){
        Publisher publisher = publisherRepository.findById(bookRequest.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Not found this publisher"));

        Book book = Book.builder()
                .isbn13(bookRequest.getIsbn13())
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .pages(bookRequest.getPages())
                .price(bookRequest.getPrice())
                .publisher(publisher)
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

    public Optional<Book> update(String id, BookRequest bookRequest){
        return bookRepository.findById(id)
                .map(b -> {
                    b.setTitle(bookRequest.getTitle());
                    b.setDescription(bookRequest.getDescription());
                    b.setPrice(bookRequest.getPrice());
                    b.setIsbn13(bookRequest.getIsbn13());
                    b.setPublisher(publisherRepository.findById(bookRequest.getPublisherId())
                            .orElseThrow(() -> new RuntimeException("Not found this publisher")));
                    return bookRepository.save(b);
                });
    }

    public void deleteById(String id){
        bookRepository.deleteById(id);
    }
}
