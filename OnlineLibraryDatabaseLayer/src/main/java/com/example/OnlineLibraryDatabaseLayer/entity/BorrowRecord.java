package com.example.OnlineLibraryDatabaseLayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "borrow_records")
@Getter
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_record_id")
    private Long id;

    @Column(name = "borrower_email")
    @Setter
    private String borrowerEmail;

    @Column(name = "borrowed_at")
    @Setter
    private LocalDate borrowedAt;

    @Column(name = "returned_at")
    @Setter
    private LocalDate returnedAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private final String identifyUUID = UUID.randomUUID().toString();

    public void setBook(Book book) {
        this.book = book;
        book.addBorrowRecord(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BorrowRecord that = (BorrowRecord) o;
        return Objects.equals(identifyUUID, that.identifyUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUUID);
    }
}
