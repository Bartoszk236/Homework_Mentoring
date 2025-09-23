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
@Table(
        name = "authors",
        indexes = {
                @Index(name = "idx_name", columnList = "name")
        }
)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Book> books = new HashSet<>();

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private String identifyUUID;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDate updatedDate;

    public Author() {
        this.identifyUUID = UUID.randomUUID().toString();
    }

    void addBook(Book book) {
        books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(identifyUUID, author.identifyUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUUID);
    }
}
