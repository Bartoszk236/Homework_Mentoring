package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository repository;
    @Autowired
    private TestEntityManager em;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setAuthor("Jan Kochanowski");
        book1.setPrice(new BigDecimal("10.99"));
        book1.setPublishedDate(LocalDate.of(2000, 1, 1));
        book1.setTitle("Pieśni Jana Kochanowskiego");
        book1.setGenre("liryka");
        em.persist(book1);

        book2 = new Book();
        book2.setAuthor("Jan Brzechwa");
        book2.setPrice(new BigDecimal("13.99"));
        book2.setPublishedDate(LocalDate.of(2001, 2, 2));
        book2.setTitle("Akademia Pana Kleksa");
        book2.setGenre("fantastyka");
        em.persist(book2);

        book3 = new Book();
        book3.setAuthor("Henryk Sienkiewicz");
        book3.setPrice(new BigDecimal("12.99"));
        book3.setPublishedDate(LocalDate.of(2003, 3, 3));
        book3.setTitle("Ogniem i Mieczem");
        book3.setGenre("powieść historyczna");
        em.persist(book3);

        em.flush();
        em.clear();
    }

    @Test
    void test_findBooksByAuthorContainsIgnoreCase() {
        //given
        String authorFirstName = "jan";
        List<Book> expectedBooks = List.of(book1, book2);

        //when
        List<Book> result = repository.findBooksByAuthorContainsIgnoreCase(authorFirstName);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void test_findBooksByPriceLessThan() {
        //given
        BigDecimal lessThan = new BigDecimal("13.50");
        List<Book> expectedBooks = List.of(book1, book3);

        //when
        List<Book> result = repository.findBooksByPriceLessThan(lessThan);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void test_findBooksByPublishedDateAfter() {
        //given
        LocalDate afterThan = LocalDate.of(2000, 5, 5);
        List<Book> expectedBooks = List.of(book2, book3);

        //when
        List<Book> result = repository.findBooksByPublishedDateAfter(afterThan);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void test_findBooksByTitleContaining() {
        //given
        String pieceOfTitle = "Pana";
        List<Book> expectedBooks = List.of(book2);

        //when
        List<Book> result = repository.findBooksByTitleContaining(pieceOfTitle);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void test_findBooksByGenreStartingWith() {
        //given
        String prefix = "powieść";
        List<Book> expectedBooks = List.of(book3);

        //when
        List<Book> result = repository.findBooksByGenreStartingWith(prefix);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void test_findBooksByTitleIgnoreCase() {
        //given
        String title = "OGNIEM I MIECZEM";
        List<Book> expectedBooks = List.of(book3);

        //when
        List<Book> result = repository.findBooksByTitleIgnoreCase(title);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedBooks);
    }
}
