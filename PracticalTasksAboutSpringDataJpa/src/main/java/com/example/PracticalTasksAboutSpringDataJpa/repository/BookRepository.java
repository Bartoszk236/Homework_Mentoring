package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByAuthorContainsIgnoreCase(String authorFirstName);

    List<Book> findBooksByPriceLessThan(BigDecimal lessThan);

    List<Book> findBooksByPublishedDateAfter(LocalDate pastThan);
}
