package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Book;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository repository;
    @Autowired
    private TestEntityManager em;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            em.persist(randomBook());
        }

        em.flush();
        em.clear();
    }

    @Test
    void test_findBooksByAuthorContainsIgnoreCase() {
        //given
        String authorFirstName = "jan";
        Book book = randomBook(book1 -> book1.setAuthor("Jan Nowak"));
        em.persist(book);
        em.flush();
        em.clear();

        //when
        List<Book> result = repository.findBooksByAuthorContainsIgnoreCase(authorFirstName);

        //then
        result.stream()
                .map(Book::getAuthor)
                .forEach(author -> {
                    System.out.println(author);
                    assertTrue(author.compareToIgnoreCase(authorFirstName) > 0);
                });
    }

    @Test
    void test_findBooksByPriceLessThan() {
        //given
        BigDecimal givenLessThan = new BigDecimal("50.0");

        //when
        List<Book> result = repository.findBooksByPriceLessThan(givenLessThan);

        //then
        result.stream()
                .map(Book::getPrice)
                .forEach(price -> assertTrue(price.compareTo(givenLessThan) < 0));
    }

    @Test
    void test_findBooksByPublishedDateAfter() {
        //given
        LocalDate afterThan = LocalDate.now().minusYears(12);

        //when
        List<Book> result = repository.findBooksByPublishedDateAfter(afterThan);

        //then
        result.stream()
                .map(Book::getPublishedDate)
                .forEach(publishedDate -> assertTrue(publishedDate.isAfter(afterThan)));
    }

    @Test
    void test_findBooksByTitleContaining() {
        //given
        String pieceOfTitle = "Pana";
        Book book = randomBook(book1 -> book1.setTitle("Akademia Pana Kleksa"));
        em.persist(book);
        em.flush();
        em.clear();

        //when
        List<Book> result = repository.findBooksByTitleContaining(pieceOfTitle);

        //then
        result.stream()
                .map(Book::getTitle)
                .forEach(title -> assertTrue(title.contains(pieceOfTitle)));
    }

    @Test
    void test_findBooksByGenreStartingWith() {
        //given
        String genre = faker.book().genre();
        String prefix = genre.substring(0, 3);
        Book book = randomBook(book1 -> book1.setGenre(genre));
        em.persist(book);
        em.flush();
        em.clear();

        //when
        List<Book> result = repository.findBooksByGenreStartingWith(prefix);

        //then
        result.stream()
                .map(Book::getGenre)
                .forEach(resultGenre -> assertTrue(resultGenre.startsWith(prefix)));
    }

    @Test
    void test_findBooksByTitleIgnoreCase() {
        //given
        String title = faker.book().title();
        Book book = randomBook(book1 -> book1.setTitle(title));
        em.persist(book);
        em.flush();
        em.clear();

        //when
        List<Book> result = repository.findBooksByTitleIgnoreCase(title);

        //then
        result.stream()
                .map(Book::getTitle)
                .forEach(resultTitle -> assertEquals(title, resultTitle));
    }

    private Book randomBook() {
        Book book = new Book();
        book.setAuthor(faker.book().author());
        book.setTitle(faker.book().title());
        book.setGenre(faker.book().genre());
        book.setPrice(new BigDecimal(faker.number().numberBetween(1, 100)));
        book.setPublishedDate(randomLocalDateBetween(
                LocalDate.now().minusYears(25),
                LocalDate.now()
        ));
        return book;
    }

    private Book randomBook(Consumer<Book> customizer) {
        Book book = randomBook();
        customizer.accept(book);
        return book;
    }

    private LocalDate randomLocalDateBetween(LocalDate start, LocalDate end) {
        long startEpoch = start.toEpochDay();
        long endEpoch = end.toEpochDay();
        long randomDay = faker.number().numberBetween(startEpoch, endEpoch + 1);
        return LocalDate.ofEpochDay(randomDay);
    }
}
