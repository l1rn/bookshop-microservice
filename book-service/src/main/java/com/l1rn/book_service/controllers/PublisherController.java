package com.l1rn.book_service.controllers;

import com.l1rn.book_service.dto.PublisherRequest;
import com.l1rn.book_service.models.Publisher;
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
public class PublisherController {
    @Autowired
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        return ResponseEntity.ok(publisherService.getAll());
    }

    @PostMapping("/publishers/add")
    public ResponseEntity<?> createPublisher(PublisherRequest request){
        try {
            return ResponseEntity.ok(publisherService.addPublisher(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't to create new publisher");
        }
    }

    @GetMapping("/publishers/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable String id){
        return ResponseEntity.ok(publisherService.getById(id));
    }

    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<?> deletePublisherById(@PathVariable String id){
        publisherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
