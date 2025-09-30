package com.example.OnlineLibraryDatabaseLayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "authors")
@Getter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Column(name = "first_name")
    @Setter
    private String firstName;

    @Column(name = "last_name")
    @Setter
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private final String identifyUUID = UUID.randomUUID().toString();

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

    void addBook(Book book) {
        books.add(book);
    }
}
