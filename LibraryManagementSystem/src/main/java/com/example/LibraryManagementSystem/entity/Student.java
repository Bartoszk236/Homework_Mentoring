package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "students")
@Getter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "email")
    @Email(message = "invalid student e-mail")
    private String email;

    @OneToMany(mappedBy = "student")
    private Set<BorrowRecord> borrowRecords = new HashSet<>();

    @Column(name = "created_date")
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDate updatedDate;

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private String identifyUUID;

    public Student() {
        this.identifyUUID = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(identifyUUID, student.identifyUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUUID);
    }

    void addBorrowRecord(BorrowRecord borrowRecord) {
        borrowRecords.add(borrowRecord);
    }
}
