package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(
        name = "books",
        indexes = {
                @Index(name = "idx_title", columnList = "title"),
                @Index(name = "idx_isbn", columnList = "isbn")
        }
)
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Column(name = "isbn", nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{13}$", message = "ISBN must have 13 digits")
    private String isbn;

    @Setter
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Setter
    @Column(name = "digital_copy_available")
    private Boolean digitalCopyAvailable;

    @Setter
    @Column(name = "pages_number")
    private Integer pagesNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = { "book_id", "author_id" })
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "book")
    private Set<BorrowRecord> borrowRecords = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "identify_uuid", updatable = false, unique = true)
    private String identifyUUID;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private LocalDate updatedDate;

    public Book() {
        this.identifyUUID = UUID.randomUUID().toString();
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.addBook(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.setBook(this);
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
        publisher.addBook(this);
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
        borrowRecords.add(borrowRecord);
    }
}
