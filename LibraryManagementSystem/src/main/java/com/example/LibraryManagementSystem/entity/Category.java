package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name")
    @Setter
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Book> books = new HashSet<>();

    @Column(name = "created_date")
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDate updatedDate;

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private String identifyUUID;

    public Category() {
        this.identifyUUID = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(identifyUUID, category.identifyUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUUID);
    }

    void setBook(Book book) {
        this.books.add(book);
    }
}
