package com.example.LibraryManagementSystem.utils;

import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowRecord;
import jakarta.persistence.criteria.*;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;

@UtilityClass
public class BookSpecUtils {
    public Specification<Book> titleContains(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), like(title));
    }

    public Specification<Book> authorNameIn(Collection<String> authorNames) {
        return (root, query, cb) -> {
            Join<Book, Author> authorJoin = root.join("authors", JoinType.LEFT);
            Predicate or = cb.or(
                    authorNames.stream()
                            .filter(s -> s != null && !s.isBlank())
                            .map(s -> cb.like(cb.lower(authorJoin.get("name")), like(s)))
                            .toArray(Predicate[]::new)
            );
            if (or.getExpressions().isEmpty()) return cb.conjunction();
            query.distinct(true);
            return or;
        };
    }

    public Specification<Book> categoryNameContains(String categoryName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("category").get("name")), like(categoryName));
    }

    public Specification<Book> publishedAfterYear(int year) {
        return (root, query, cb) -> {
            LocalDate date = LocalDate.of(year + 1, 1, 1);
            return cb.greaterThan(root.get("releaseDate"), date);
        };
    }

    public Specification<Book> publishedBeforeYear(int year) {
        return (root, query, cb) -> {
            LocalDate date = LocalDate.of(year, 1, 1);
            return cb.lessThan(root.get("releaseDate"), date);
        };
    }

    public Specification<Book> pagesNumberGreaterThanOrEqual(int min) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("pagesNumber"), min);
    }

    public Specification<Book> pagesNumberLessThanOrEqual(int max) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("pagesNumber"), max);
    }

    public Specification<Book> isbnEquals(String isbn) {
        return (root, query, cb) ->
                cb.equal(root.get("isbn"), isbn);
    }

    public Specification<Book> digitalCopyRequired(boolean required) {
        return (root, query, cb) ->
                cb.equal(root.get("digitalCopyAvailable"), required);
    }

    public Specification<Book> availableOnly() {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<BorrowRecord> borrowRecordRoot = subquery.from(BorrowRecord.class);
            subquery.select(cb.literal(1L));
            Predicate sameBook = cb.equal(borrowRecordRoot.get("book").get("id"), root.get("id"));
            Predicate openBorrow = cb.isNull(borrowRecordRoot.get("returnDate"));
            subquery.where(sameBook, openBorrow);
            return cb.not(cb.exists(subquery));
        };
    }

    private String like(String s) {
        String t = s.trim().toLowerCase();
        return "%" + t + "%";
    }
}
