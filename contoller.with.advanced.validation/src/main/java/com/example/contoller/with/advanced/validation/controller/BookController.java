package com.example.contoller.with.advanced.validation.controller;

import com.example.contoller.with.advanced.validation.entity.Book;
import com.example.contoller.with.advanced.validation.dto.BookRequest;
import com.example.contoller.with.advanced.validation.service.BookService;
import com.example.contoller.with.advanced.validation.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@Validated({ValidationGroups.class, ValidationGroups.Create.class}) @RequestBody BookRequest request) {
        bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "title") String keyword,
            @RequestParam(defaultValue = "true") boolean isASC
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll(page, size, keyword, isASC));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@Validated({ValidationGroups.class, ValidationGroups.Update.class}) @RequestBody BookRequest request, @PathVariable Long id) {
        bookService.updateBook(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
