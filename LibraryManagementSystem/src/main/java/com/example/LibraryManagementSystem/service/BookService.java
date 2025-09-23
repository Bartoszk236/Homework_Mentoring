package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookSearchCriteria;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowRecord;
import com.example.LibraryManagementSystem.entity.Category;
import com.example.LibraryManagementSystem.repository.BookRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Specification<Book> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (criteria.title() != null) {
                predicates.add(cb.like(cb.lower(root.get("title")), like(criteria.title())));
            }

            if (criteria.authorNames() != null && !criteria.authorNames().isEmpty()) {
                Join<Book, Author> join = root.join("authors", JoinType.LEFT);

                List<Predicate> ors = new ArrayList<>();
                for (String authorName : criteria.authorNames()) {
                    ors.add(cb.like(cb.lower(join.get("name")), like(authorName)));
                }
                if (!ors.isEmpty()) {
                    predicates.add(cb.or(ors.toArray(Predicate[]::new)));
                    query.distinct(true);
                }
            }

            if (criteria.categoryName() != null) {
                predicates.add(cb.like(cb.lower(root.get("category").get("name")), like(criteria.categoryName())));
            }

            if (criteria.publishedAfterYear() != null) {
                LocalDate publishedAfterYear = LocalDate.of(criteria.publishedAfterYear() + 1, 1, 1);
                predicates.add(cb.greaterThan(root.get("releaseDate"), publishedAfterYear));
            }

            if (criteria.publishedBeforeYear() != null) {
                LocalDate publishedBeforeYear = LocalDate.of(criteria.publishedBeforeYear(), 1, 1);
                predicates.add(cb.lessThan(root.get("releaseDate"), publishedBeforeYear));
            }

            if (criteria.availableOnly() != null) {
                if (criteria.availableOnly()) {
                    Subquery<Long> sq = query.subquery(Long.class);
                    Root<BorrowRecord> rootBorrow = sq.from(BorrowRecord.class);
                    sq.select(cb.literal(1L));
                    Predicate sameBook = cb.equal(rootBorrow.get("book").get("id"),
                            root.get("id")
                    );
                    Predicate openBorrow = cb.isNull(rootBorrow.get("returnDate"));
                    sq.where(sameBook, openBorrow);
                    predicates.add(cb.not(cb.exists(sq)));
                }
            }


            if (criteria.minPages() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("pagesNumber"), criteria.minPages()));
            }

            if (criteria.maxPages() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("pagesNumber"), criteria.maxPages()));
            }

            if (criteria.isbn() != null) {
                predicates.add(cb.equal(root.get("isbn"), criteria.isbn()));
            }

            if (criteria.digitalCopyRequired() != null) {
                predicates.add(cb.equal(root.get("digitalCopyAvailable"), criteria.digitalCopyRequired()));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(Predicate[]::new));
        };

        return bookRepository.findAll(spec, pageable);
    }

    private String like(String s) {
        String t = s.trim().toLowerCase();
        return "%" + t + "%";
    }
}
