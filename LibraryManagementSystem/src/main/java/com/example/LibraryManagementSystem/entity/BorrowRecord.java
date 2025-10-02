package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(name = "borrow_date", nullable = false)
    @PastOrPresent
    @Setter
    private LocalDate borrowDate;

    @Column(name = "return_date")
    @PastOrPresent
    @Setter
    private LocalDate returnDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private String identifyUUID = UUID.randomUUID().toString();

    @Column(name = "created_date")
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDate updatedDate;

    public void setBook(Book book) {
        this.book = book;
        book.addBorrowRecord(this);
    }

    public void setStudent(Student student) {
        this.student = student;
        student.addBorrowRecord(this);
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
