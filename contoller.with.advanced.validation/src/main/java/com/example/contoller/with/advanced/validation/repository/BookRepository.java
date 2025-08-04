package com.example.contoller.with.advanced.validation.repository;

import com.example.contoller.with.advanced.validation.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    void deleteById(Long id);
}
