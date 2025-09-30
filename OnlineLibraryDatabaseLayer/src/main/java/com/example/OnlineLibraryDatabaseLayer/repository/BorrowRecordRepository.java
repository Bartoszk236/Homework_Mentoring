package com.example.OnlineLibraryDatabaseLayer.repository;

import com.example.OnlineLibraryDatabaseLayer.entity.Book;
import com.example.OnlineLibraryDatabaseLayer.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByBook(Book book);
}
