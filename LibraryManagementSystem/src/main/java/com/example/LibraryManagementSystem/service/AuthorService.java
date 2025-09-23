package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowRecord;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Page<Author> getAuthorsByMostBorrowCounts(int page, int pageSize) {
        Specification<Author> specification = (root, query, cb) -> {
            boolean isCount = Long.class.equals(query.getResultType());
            if (!isCount) {
                Join<Author, Book> joinBooks = root.join("books", JoinType.LEFT);
                Join<Book, BorrowRecord> joinBorrowRecord = joinBooks.join("borrowRecords", JoinType.LEFT);
                Expression<Long> borrowCount = cb.count(joinBorrowRecord);

                query.groupBy(root);
                query.orderBy(cb.desc(borrowCount));
            }
            return cb.conjunction();
        };

        PageRequest pageRequestWithoutSorting = PageRequest.of(page, pageSize);
        return authorRepository.findAll(specification, pageRequestWithoutSorting);
    }
}
