package com.l1rn.book_service.services;

import com.l1rn.book_service.dto.PublisherRequest;
import com.l1rn.book_service.models.Book;
import com.l1rn.book_service.models.Publisher;
import com.l1rn.book_service.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {
    @Autowired
    private final PublisherRepository publisherRepository;

    @Autowired
    private final BookService bookService;

    public List<Publisher> getAll(){
        return publisherRepository.findAll();
    }

    public Publisher addPublisher(PublisherRequest request){
        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .acronym(request.getAcronym())
                .description(request.getDescription())
                .foundationYear(request.getFoundationYear())
                .build();
        return publisherRepository.save(publisher);
    }

    public Publisher getById(String id){
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found this publisher"));
    }

    public Publisher addBookToPublisher(String id, String bookId){
        Book book = bookService.getById(bookId);
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found this publisher"));

        publisher.setBooks(List.of(book));
        publisherRepository.save(publisher);
        return publisher;
    }

    public void deleteById(String id){
        publisherRepository.deleteById(id);
    }
}
