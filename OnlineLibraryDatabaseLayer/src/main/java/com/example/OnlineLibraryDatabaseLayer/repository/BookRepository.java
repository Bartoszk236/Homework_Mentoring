package com.example.OnlineLibraryDatabaseLayer.repository;

import com.example.OnlineLibraryDatabaseLayer.entity.Author;
import com.example.OnlineLibraryDatabaseLayer.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
            select b
            from Book b
            where b.author = :author
            and not exists (
            select 1
            from BorrowRecord br
            where br.book = b and br.returnedAt is null
            )
            """)
    @EntityGraph("borrowRecords")
    List<Book> findAllNotBorrowedBooksByAuthor(@Param("author") Author author);

    @Query(value = """
            select b.*
            from books b
            left join borrow_records br on br.book_id = b.book_id
            group by b.book_id
            order by count(br.borrow_record_id) desc, b.book_id desc
            limit 5
            """, nativeQuery = true)
    List<Book> findTop5BooksByBorrowsCount();

    @Query("""
            select b
            from Book b
            join b.borrowRecords br
            where br.borrowedAt > :after
            and br.returnedAt is null
            order by b.title asc, b.id asc
            """)
    @EntityGraph("borrowRecords")
    Page<Book> findBooksBorrowedAfterDateOrderByTitle(@Param("after") LocalDate after, Pageable pageable);
}
