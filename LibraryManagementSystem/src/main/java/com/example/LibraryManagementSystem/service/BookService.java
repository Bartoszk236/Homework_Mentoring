package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookSearchCriteria;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Category;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.utils.BookSpecUtils;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.LibraryManagementSystem.utils.BookSpecUtils.*;
import static com.example.LibraryManagementSystem.utils.SpecUtils.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getBooksByTitleSampleAndCategory(String titleSample, String category) {
        Category probeCategory = new Category();
        probeCategory.setName(category);

        Book probeBook = new Book();
        probeBook.setTitle(titleSample);
        probeBook.setCategory(probeCategory);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatcher
                        .of(ExampleMatcher.StringMatcher.CONTAINING)
                        .ignoreCase()
                )
                .withMatcher("category.name", ExampleMatcher.GenericPropertyMatcher
                        .of(ExampleMatcher.StringMatcher.EXACT)
                )
                .withIgnorePaths("identifyUUID", "category.identifyUUID");

        Example<Book> example = Example.of(probeBook, exampleMatcher);
        return bookRepository.findAll(example);
    }

    public List<Book> getNotRentedBooks() {
        return bookRepository.findAllNotBorrowedBooks();
    }

    public Page<Book> searchBooks(BookSearchCriteria criteria, Pageable pageable) {
        Specification<Book> specification = Specification.allOf(
                List.of(
                        optional(criteria.title(), BookSpecUtils::titleContains),
                        optionalNotEmpty(criteria.authorNames(), BookSpecUtils::authorNameIn),
                        optional(criteria.categoryName(), BookSpecUtils::categoryNameContains),
                        optional(criteria.publishedAfterYear(), BookSpecUtils::publishedAfterYear),
                        optional(criteria.publishedBeforeYear(), BookSpecUtils::publishedBeforeYear),
                        optional(criteria.minPages(), BookSpecUtils::pagesNumberGreaterThanOrEqual),
                        optional(criteria.maxPages(), BookSpecUtils::pagesNumberLessThanOrEqual),
                        optional(criteria.isbn(), BookSpecUtils::isbnEquals),
                        optional(criteria.digitalCopyRequired(), BookSpecUtils::digitalCopyRequired),
                        Boolean.TRUE.equals(criteria.availableOnly()) ? availableOnly() : null
                ));
        return bookRepository.findAll(specification, pageable);
    }
}
