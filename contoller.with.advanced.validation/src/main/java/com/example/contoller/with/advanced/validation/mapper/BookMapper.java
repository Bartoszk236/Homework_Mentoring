package com.example.contoller.with.advanced.validation.mapper;

import com.example.contoller.with.advanced.validation.dto.BookRequest;
import com.example.contoller.with.advanced.validation.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toBook(BookRequest request) {
        return Book.builder()
                .title(request.title())
                .author(request.author())
                .isbn(request.isbn())
                .price(request.price())
                .publishDate(request.publishDate())
                .description(request.description())
                .build();
    }

    public Book toBook(Book book, BookRequest request) {
        return Book.builder()
                .bookId(book.getBookId())
                .title(request.title())
                .author(request.author())
                .isbn(request.isbn())
                .price(request.price())
                .publishDate(request.publishDate())
                .description(request.description())
                .build();
    }
}
