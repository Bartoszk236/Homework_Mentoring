package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowRecord;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import(AuthorService.class)
class AuthorServiceTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private AuthorService authorService;
    private final Faker faker = new Faker();

    @Test
    void givenFewAuthorsWithBooksWithBorrowsWhenGetAuthorsByMostBorrowCountsThenReturnPageWithBorrowsCountDescOrder() {
        //given
        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();

        Book book1 = randomBook(author1);
        em.persist(book1);
        Book book2 = randomBook(author2);
        em.persist(book2);
        Book book3 = randomBook(author3);
        em.persist(book3);

        BorrowRecord borrowRecord1 = new BorrowRecord();
        borrowRecord1.setBorrowDate(LocalDate.now().minusDays(2));
        borrowRecord1.setReturnDate(LocalDate.now().minusDays(1));
        borrowRecord1.setBook(book2);
        em.persist(borrowRecord1);

        BorrowRecord borrowRecord2 = new BorrowRecord();
        borrowRecord2.setBorrowDate(LocalDate.now().minusDays(2));
        borrowRecord2.setReturnDate(LocalDate.now().minusDays(1));
        borrowRecord2.setBook(book2);
        em.persist(borrowRecord2);

        BorrowRecord borrowRecord3 = new BorrowRecord();
        borrowRecord3.setBorrowDate(LocalDate.now().minusDays(2));
        borrowRecord3.setReturnDate(LocalDate.now().minusDays(1));
        borrowRecord3.setBook(book1);
        em.persist(borrowRecord3);

        em.flush();
        em.clear();

        //when
        Page<Author> result = authorService.getAuthorsByMostBorrowCounts(0, 3);

        //then
        assertThat(result).hasSize(3);
        assertThat(result.getContent()).containsExactly(author2, author1, author3);
    }

    private Book randomBook(Author author) {
        Book book = new Book();
        book.setTitle(faker.book().title());
        book.setIsbn(faker.regexify("[0-9]{13}"));
        book.addAuthor(author);
        return book;
    }
}
