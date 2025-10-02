package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.SampleDataBuilder;
import com.example.LibraryManagementSystem.dto.BookSearchCriteria;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowRecord;
import com.example.LibraryManagementSystem.entity.Category;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import({
        BookService.class,
        SampleDataBuilder.class
})
class BookServiceTest {
    @Autowired
    private BookService service;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private SampleDataBuilder sampleDataBuilder;
    @Autowired
    private AuthorRepository authorRepository;

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        sampleDataBuilder.initSampleData();
    }

    @Test
    void givenTitleProbeAndCategoryNameWhenGetBooksByTitleSampleAndCategoryThenReturnListOfBooks() {
        //given
        Book givenBook = findExampleBook();

        String title = givenBook.getTitle();
        String givenTitleSample = title.substring(faker.number().numberBetween(1, title.length()));
        Category category = givenBook.getCategory();
        String givenCategoryName = category.getName();

        //when
        List<Book> result = service.getBooksByTitleSampleAndCategory(givenTitleSample, givenCategoryName);

        //then
        assertThat(result).isNotEmpty();
        result.forEach(book -> {
            assertThat(book.getTitle()).contains(givenTitleSample);
            assertThat(book.getCategory().getName()).isEqualTo(givenCategoryName);
        });
    }

    @Test
    void givenFewBooksWhenGetNotRentedBooksThenReturnNotRentedBooks() {
        //when
        List<Book> result = service.getNotRentedBooks();

        //then
        assertThat(result).isNotEmpty();
        result.forEach(book -> {
            Set<BorrowRecord> borrowRecords = book.getBorrowRecords();
            boolean empty = borrowRecords.isEmpty();
            boolean existBorrowed = borrowRecords.stream()
                    .allMatch(borrowRecord -> borrowRecord.getReturnDate() != null);
            assertThat(empty || existBorrowed).isTrue();
        });
    }

    @Test
    void givenTitleWhenSearchBooksThenReturnPage() {
        //given
        Book givenBook = findExampleBook();

        String title = givenBook.getTitle();
        String givenTitleSample = title.substring(faker.number().numberBetween(1, title.length()));

        BookSearchCriteria criteria = new BookSearchCriteria(givenTitleSample, null, null, null, null, null, null, null, null, null);

        PageRequest request = PageRequest.of(0, 5);

        //then
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        assertThat(result.stream()
                .allMatch(book -> book.getTitle().contains(givenTitleSample)))
                .isTrue();
    }

    @Test
    void givenAuthorNamesWhenSearchBooksThenReturnPage() {
        //given
        List<String> givenAuthorNames = authorRepository.findAll().stream()
                .map(Author::getName)
                .limit(3)
                .toList();

        BookSearchCriteria criteria = new BookSearchCriteria(null, givenAuthorNames, null, null, null, null, null, null, null, null);

        PageRequest request = PageRequest.of(0, 5);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.stream()
                .forEach(book -> {
                    List<String> authorsNames = book.getAuthors()
                            .stream()
                            .map(Author::getName)
                            .toList();
                    assertThat(authorsNames).containsAnyElementsOf(givenAuthorNames);
                });
    }

    @Test
    void givenCategoryNameWhenSearchBooksThenReturnPage() {
        //given
        Book givenBook = findExampleBook();
        String givenCategoryName = givenBook.getCategory().getName();

        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, givenCategoryName, null, null, null, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.stream()
                .map(Book::getCategory)
                .map(Category::getName)
                .forEach(categoryName -> assertThat(categoryName).isEqualTo(givenCategoryName));
    }

    @Test
    void givenPublishedAfterYearWhenSearchBooksThenReturnPage() {
        //given
        Book givenBook = findExampleBook();

        Integer givenPublishedAfterYear= givenBook.getReleaseDate().getYear() - 1;

        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, givenPublishedAfterYear, null, null, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.stream()
                .map(Book::getReleaseDate)
                .map(LocalDate::getYear)
                .forEach(x -> assertThat(x).isGreaterThan(givenPublishedAfterYear));
    }

    @Test
    void givenPublishedBeforeYearWhenSearchBooksThenReturnPage() {
        //given
        Book givenBook = findExampleBook();

        Integer givenPublishedBeforeYear= givenBook.getReleaseDate().getYear() + 1;

        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, givenPublishedBeforeYear, null, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.stream()
                .map(Book::getReleaseDate)
                .map(LocalDate::getYear)
                .forEach(x -> assertThat(x).isLessThan(givenPublishedBeforeYear));
    }

    @Test
    void givenAvailableOnlyWhenSearchBooksThenReturnPage() {
        //given
        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, true, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.forEach(book -> {
            Set<BorrowRecord> borrowRecords = book.getBorrowRecords();
            boolean empty = borrowRecords.isEmpty();
            boolean existBorrowed = borrowRecords.stream()
                    .allMatch(borrowRecord -> borrowRecord.getReturnDate() != null);
            assertThat(empty || existBorrowed).isTrue();
        });
    }

    @Test
    void givenMinPagesWhenSearchBooksThenReturnPage() {
        //given
        Book givenBook = findExampleBook();
        Integer givenMinPages = givenBook.getPagesNumber() - 1;
        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, givenMinPages, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.stream()
                .map(Book::getPagesNumber)
                .forEach(number -> assertThat(number).isGreaterThanOrEqualTo(givenMinPages));
    }

    @Test
    void givenMaxPagesWhenSearchBooksThenReturnPage() {
        //given
        Book givenBook = findExampleBook();
        Integer givenMaxPages = givenBook.getPagesNumber() + 1;
        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, null, givenMaxPages, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then

        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.stream()
                .map(Book::getPagesNumber)
                .forEach(number -> assertThat(number).isLessThanOrEqualTo(givenMaxPages));
    }

    @Test
    void givenIsbnWhenSearchBooksThenReturnPage() {
        //given
        Book givenBook = findExampleBook();

        String givenIsbn = givenBook.getIsbn();

        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, null, null, givenIsbn, null);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then
        assertThat(result).hasSize(1);
        assertThat(givenIsbn).isEqualTo(result.getContent().getFirst().getIsbn());
    }

    @Test
    void givenDigitalCopyRequiredWhenSearchBooksThenReturnPage() {
        //given
        Boolean givenDigitalCopyRequired = true;
        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, null, null, null, givenDigitalCopyRequired);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        result.stream()
                .map(Book::getDigitalCopyAvailable)
                .forEach(available -> assertThat(available).isEqualTo(givenDigitalCopyRequired));
    }

    private Book findExampleBook() {
        return bookRepository.findAll().stream()
                .findFirst()
                .orElseThrow();
    }
}
