package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends
        JpaRepository<Book, Long>,
        QueryByExampleExecutor<Book>,
        JpaSpecificationExecutor<Book> {
    @Query("""
            select b
            from Book b
            where not exists (
            select 1
            from BorrowRecord br
            where br.book = b
            and br.returnDate is null
            )
            """)
    List<Book> findAllNotBorrowedBooks();
}
