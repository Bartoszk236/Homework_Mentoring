package com.example.OnlineLibraryDatabaseLayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@NamedEntityGraph(
        name = "borrowRecords",
        attributeNodes = @NamedAttributeNode("borrowRecords")
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title")
    @Setter
    private String title;

    @Column(name = "isbn")
    @Setter
    private String isbn;

    @Column(name = "available_copies")
    @Setter
    private Integer availableCopies;

    @Column(name = "published_date")
    @Setter
    private LocalDate publishedDate;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<BorrowRecord>  borrowRecords = new ArrayList<>();

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private final String identifyUUID = UUID.randomUUID().toString();

    public void setAuthor(Author author) {
        this.author = author;
        author.addBook(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(identifyUUID, book.identifyUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUUID);
    }

    void addBorrowRecord(BorrowRecord borrowRecord) {
        this.borrowRecords.add(borrowRecord);
    }
}
