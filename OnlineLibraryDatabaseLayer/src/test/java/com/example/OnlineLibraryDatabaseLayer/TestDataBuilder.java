package com.example.OnlineLibraryDatabaseLayer;

import com.example.OnlineLibraryDatabaseLayer.entity.Author;
import com.example.OnlineLibraryDatabaseLayer.entity.Book;
import com.example.OnlineLibraryDatabaseLayer.entity.BorrowRecord;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TestDataBuilder {
    private final Faker faker = new Faker();
    @Autowired
    private TestEntityManager em;

    public Book createBook(Author author) {
        Book book = new Book();
        book.setTitle(faker.book().title());
        book.setIsbn(faker.regexify("[0-9]{13}"));
        book.setAvailableCopies(faker.number().numberBetween(1, 100));
        book.setPublishedDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 100)));
        if (author != null) {
            book.setAuthor(author);
        }
        em.persist(book);
        return book;
    }

    public Author createAuthor() {
        Author author = new Author();
        author.setFirstName(faker.name().firstName());
        author.setLastName(faker.name().lastName());
        em.persist(author);
        return author;
    }

    public BorrowRecord createBorrowRecord(Book book, LocalDate returnDate) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBorrowerEmail(faker.internet().emailAddress());
        borrowRecord.setBorrowedAt(LocalDate.now().minusDays(faker.number().numberBetween(1, 100)));
        if (returnDate != null) borrowRecord.setReturnedAt(returnDate);
        if (book != null) borrowRecord.setBook(book);
        em.persist(borrowRecord);
        return borrowRecord;
    }

    public void initSampleData() {
        Author author1 = createAuthor();
        Author author2 = createAuthor();
        Author author3 = createAuthor();
        Author author4 = createAuthor();
        Author author5 = createAuthor();

        List<Author> authors = List.of(author1, author2, author3, author4, author5);
        authors.forEach(author -> em.persist(author));

        Book book1 = createBook(author1);
        Book book2 = createBook(author1);
        Book book3 = createBook(author1);
        Book book4 = createBook(author2);
        Book book5 = createBook(author2);
        Book book6 = createBook(author3);
        Book book7 = createBook(author3);
        Book book8 = createBook(author3);
        Book book9 = createBook(author4);
        Book book10 = createBook(author4);
        Book book11 = createBook(author4);
        Book book12 = createBook(author4);
        Book book13 = createBook(author4);
        Book book14 = createBook(author5);
        Book book15 = createBook(author5);
        Book book16 = createBook(author5);
        Book book17 = createBook(author5);
        Book book18 = createBook(author3);
        Book book19 = createBook(author2);
        Book book20 = createBook(author1);

        List<Book> books = List.of(
                book1, book2, book3, book4, book5,
                book6, book7, book8, book9, book10,
                book11, book12, book13, book14, book15,
                book16, book17, book18, book19, book20
        );
        books.forEach(book -> em.persist(book));

        em.persist(createBorrowRecord(book1, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book6, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book7, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book8, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book9, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book10, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book11, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book12, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book13, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book14, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book15, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book16, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book17, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book18, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book19, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book20, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book1, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book1, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book1, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book1, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book1, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book2, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book3, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book4, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book5, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book6, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book6, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book6, rollNullOrReturnedAt()));
        em.persist(createBorrowRecord(book6, rollNullOrReturnedAt()));

        em.flush();
        em.clear();
    }

    private LocalDate rollNullOrReturnedAt() {
        return faker.number().numberBetween(0, 1) == 0 ? null : LocalDate.now();
    }
}
