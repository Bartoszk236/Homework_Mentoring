package com.example.OnlineLibraryDatabaseLayer.repository;

import com.example.OnlineLibraryDatabaseLayer.IntegrationTestBase;
import com.example.OnlineLibraryDatabaseLayer.TestDataBuilder;
import com.example.OnlineLibraryDatabaseLayer.entity.Author;
import com.example.OnlineLibraryDatabaseLayer.entity.Book;
import com.example.OnlineLibraryDatabaseLayer.entity.BorrowRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({TestDataBuilder.class})
class BookRepositoryTest extends IntegrationTestBase {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager em;
    @Autowired
    private TestDataBuilder testDataBuilder;
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @BeforeEach
    void setUp() {
        testDataBuilder.initSampleData();
    }

    @Test
    void givenAuthorWhenFindAllNotBorrowedBooksByAuthorThenReturnBooksByAuthorAndNotBorrowed() {
        //given
        Author author = em.find(Author.class, 1L);

        //when
        List<Book> result = bookRepository.findAllNotBorrowedBooksByAuthor(author);

        //then
        result.forEach(book -> assertEquals(author, book.getAuthor()));
        result.stream()
                .map(Book::getBorrowRecords)
                .flatMap(List::stream)
                .forEach(borrowRecord -> assertNotNull(borrowRecord.getReturnedAt()));
    }

    @Test
    void givenExampleDataWhenFindTop5BooksByBorrowsCountThenReturnBooksOrderByCountOfBorrows() {
        //when
        List<Book> result = bookRepository.findTop5BooksByBorrowsCount();

        //then
        assertThat(result).hasSize(5);

        List<Book> expectedOrder = result.stream()
                .sorted(Comparator.comparingInt((Book b) -> b.getBorrowRecords().size())
                        .thenComparing(Book::getId)
                        .reversed()
                )
                .toList();
        assertThat(result).containsExactlyElementsOf(expectedOrder);

        assertThat(result.stream()
                .map(Book::getTitle)
                .toList()).isNotEmpty();
    }

    @Test
    void givenDateAfterAndRequestWhenFindBooksBorrowedAfterDateOrderByTitleThenReturnPageBooksWithBorrowedAfterDateAndOrderByTitle() {
        //given
        LocalDate givenAfter = LocalDate.now().minusDays(101);
        Pageable request = PageRequest.of(0, 5);

        //when
        Page<Book> result = bookRepository.findBooksBorrowedAfterDateOrderByTitle(givenAfter, request);

        //then
        assertThat(result).hasSize(5);

        result.stream()
                .map(Book::getBorrowRecords)
                .flatMap(List::stream)
                .map(BorrowRecord::getBorrowedAt)
                .forEach(borrowAt -> assertTrue(borrowAt.isAfter(givenAfter)));

        Collator coll = Collator.getInstance(Locale.ROOT);
        List<Book> expectedOrder = result.stream()
                .sorted(Comparator.comparing(Book::getTitle, coll)
                        .thenComparing(Book::getId)
                )
                .toList();
        assertThat(result).containsExactlyElementsOf(expectedOrder);
    }

    @Test
    void givenBookWithoutAuthorWhenSaveThenThrowDataIntegrityViolationException() {
        //given
        Book book = new Book();

        //when / then
        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(book));
    }

    @Test
    void givenTestDataWhenDeleteBookThenDeleteAlsoBorrowRecords() {
        //given
        Book book = bookRepository.findAll()
                .stream()
                .findAny()
                .orElseThrow(() -> new AssertionError("Book not found"));

        assertThat(book.getBorrowRecords().size()).isGreaterThan(0);

        //when
        bookRepository.delete(book);
        em.flush();
        em.clear();

        //then
        List<BorrowRecord> br = borrowRecordRepository.findByBook(book);
        assertThat(br).isEmpty();
    }
}
