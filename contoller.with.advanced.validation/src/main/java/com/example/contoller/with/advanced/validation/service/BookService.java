package com.example.contoller.with.advanced.validation.service;

import com.example.contoller.with.advanced.validation.dto.BookRequest;
import com.example.contoller.with.advanced.validation.entity.Book;
import com.example.contoller.with.advanced.validation.mapper.BookMapper;
import com.example.contoller.with.advanced.validation.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public void createBook(BookRequest request) {
        bookRepository.save(bookMapper.toBook(request));
    }

    public Page<Book> getAll(int page, int size, String sortBy, boolean isASC) {
        Pageable pageable = PageRequest.of(page, size, isASC ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return bookRepository.findAll(pageable);
    }

    public void updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("book not found"));
        bookRepository.save(bookMapper.toBook(book, request));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
