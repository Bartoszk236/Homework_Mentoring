package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookSearchCriteria;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowRecord;
import com.example.LibraryManagementSystem.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(BookService.class)
class BookServiceTest {
    @Autowired
    private BookService service;
    @Autowired
    private TestEntityManager em;

    @Test
    void givenTitleProbeAndCategoryNameWhenGetBooksByTitleSampleAndCategoryThenReturnListOfBooks() {
        //given
        String titleSample = "rry Pot";
        String categoryName = "Fantasy";

        Category category = new Category();
        category.setName(categoryName);
        em.persist(category);

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        book.setCategory(category);
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setCategory(category);
        em.persist(book2);

        em.flush();
        em.clear();

        //when
        List<Book> result = service.getBooksByTitleSampleAndCategory(titleSample, categoryName);

        //then
        assertThat(result).hasSize(1);
        Book resultBook = result.getFirst();
        assertEquals(book, resultBook);
    }

    @Test
    void givenFewBooksWhenGetNotRentedBooksThenReturnNotRentedBooks() {
        //given
        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setIsbn("0000000000000");
        em.persist(book1);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        em.persist(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter2");
        book3.setIsbn("0000000000002");
        em.persist(book3);

        BorrowRecord borrowRecord1 = new BorrowRecord();
        borrowRecord1.setBook(book1);
        borrowRecord1.setBorrowDate(LocalDate.now().minusDays(1));
        em.persist(borrowRecord1);

        BorrowRecord borrowRecord2 = new BorrowRecord();
        borrowRecord2.setBook(book2);
        borrowRecord2.setBorrowDate(LocalDate.now().minusDays(2));
        borrowRecord2.setReturnDate(LocalDate.now());
        em.persist(borrowRecord2);

        em.flush();
        em.clear();

        List<Book> expected = List.of(book2, book3);

        //when
        List<Book> result = service.getNotRentedBooks();

        //then
        assertThat(result).hasSize(expected.size());
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void givenTitleWhenSearchBooksThenReturnPage() {
        //given
        String givenTitle = "arry pott";
        BookSearchCriteria criteria = new BookSearchCriteria(givenTitle, null, null, null, null, null, null, null, null, null);

        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setIsbn("0000000000000");
        em.persist(book1);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        em.persist(book2);

        em.flush();
        em.clear();

        PageRequest request = PageRequest.of(0, 5);

        //then
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).hasSize(1);
        List<Book> resultBooks = result.getContent();
        assertEquals(book1, resultBooks.getFirst());
    }

    @Test
    void givenAuthorNamesWhenSearchBooksThenReturnPage() {
        //given
        List<String> givenAuthorNames = List.of("John Doe", "Jerry Smith");

        Author author1 = new Author();
        author1.setName("John Doe");

        Author author2 = new Author();
        author2.setName("Jerry Smith");

        Author author3 = new Author();
        author3.setName("Richard Cash");

        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setIsbn("0000000000000");
        book1.addAuthor(author1);
        em.persist(book1);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.addAuthor(author2);
        em.persist(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter2");
        book3.setIsbn("0000000000002");
        book3.addAuthor(author3);
        em.persist(book3);

        em.flush();
        em.clear();

        BookSearchCriteria criteria = new BookSearchCriteria(null, givenAuthorNames, null, null, null, null, null, null, null, null);

        PageRequest request = PageRequest.of(0, 5);

        //when
        Page<Book> result = service.searchBooks(criteria, request);
        assertThat(result).hasSize(2);
        List<Book> resultBooks = result.getContent();
        assertThat(resultBooks).containsExactlyInAnyOrderElementsOf(List.of(book1, book2));
    }

    @Test
    void givenCategoryNameWhenSearchBooksThenReturnPage() {
        //given
        String categoryName = "Fantasy";

        Category category = new Category();
        category.setName(categoryName);
        em.persist(category);

        Category category2 = new Category();
        category2.setName("Something");
        em.persist(category2);

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        book.setCategory(category);
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setCategory(category);
        em.persist(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter2");
        book3.setIsbn("0000000000002");
        book3.setCategory(category2);
        em.persist(book3);

        em.flush();
        em.clear();

        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, categoryName, null, null, null, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).hasSize(2);
        List<Book> resultBooks = result.getContent();
        assertThat(resultBooks).containsExactlyInAnyOrderElementsOf(List.of(book, book2));
    }

    @Test
    void givenPublishedAfterYearWhenSearchBooksThenReturnPage() {
        //given
        Integer givenPublishedAfterYear = 2001;

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        book.setReleaseDate(LocalDate.now());
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setReleaseDate(LocalDate.now().withYear(2000));
        em.persist(book2);

        em.flush();
        em.clear();

        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, givenPublishedAfterYear, null, null, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).hasSize(1);
        Book resultBook = result.getContent().getFirst();
        assertEquals(book, resultBook);
    }

    @Test
    void givenPublishedBeforeYearWhenSearchBooksThenReturnPage() {
        //given
        Integer givenPublishedBeforeYear = 2001;

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        book.setReleaseDate(LocalDate.now());
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setReleaseDate(LocalDate.of(2000, 12, 31));
        em.persist(book2);

        em.flush();
        em.clear();

        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, givenPublishedBeforeYear, null, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).hasSize(1);
        Book resultBook = result.getContent().getFirst();
        assertEquals(book2, resultBook);
    }

    @Test
    void givenAvailableOnlyWhenSearchBooksThenReturnPage() {
        //given
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        em.persist(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter2");
        book3.setIsbn("0000000000002");
        em.persist(book3);

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);
        borrowRecord.setBorrowDate(LocalDate.now().minusDays(1));
        em.persist(borrowRecord);

        BorrowRecord borrowRecord2 = new BorrowRecord();
        borrowRecord2.setBook(book2);
        borrowRecord2.setBorrowDate(LocalDate.now().minusDays(2));
        borrowRecord2.setReturnDate(LocalDate.now().minusDays(1));
        em.persist(borrowRecord2);

        em.flush();
        em.clear();

        PageRequest request = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, true, null, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, request);

        //then
        assertThat(result).hasSize(2);
        List<Book> resultBooks = result.getContent();
        assertThat(resultBooks).containsExactlyInAnyOrderElementsOf(List.of(book2, book3));
    }

    @Test
    void givenMinPagesWhenSearchBooksThenReturnPage() {
        //given
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        book.setPagesNumber(300);
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setPagesNumber(400);
        em.persist(book2);

        em.flush();
        em.clear();

        Integer givenMinPages = 350;
        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, givenMinPages, null, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then
        assertThat(result).hasSize(1);
        Book resultBook = result.getContent().getFirst();
        assertEquals(book2, resultBook);
    }

    @Test
    void givenMaxPagesWhenSearchBooksThenReturnPage() {
        //given
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        book.setPagesNumber(300);
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setPagesNumber(400);
        em.persist(book2);

        em.flush();
        em.clear();

        Integer givenMaxPages = 350;
        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, null, givenMaxPages, null, null);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then
        assertThat(result).hasSize(1);
        Book resultBook = result.getContent().getFirst();
        assertEquals(book, resultBook);
    }

    @Test
    void givenIsbnWhenSearchBooksThenReturnPage() {
        //given
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        em.persist(book2);

        em.flush();
        em.clear();

        String givenIsbn = "0000000000000";

        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, null, null, givenIsbn, null);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then
        assertThat(result).hasSize(1);
        Book resultBook = result.getContent().getFirst();
        assertEquals(book, resultBook);
    }

    @Test
    void givenDigitalCopyRequiredWhenSearchBooksThenReturnPage() {
        //given
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setIsbn("0000000000000");
        book.setDigitalCopyAvailable(true);
        em.persist(book);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setDigitalCopyAvailable(false);
        em.persist(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter2");
        book3.setIsbn("0000000000002");
        em.persist(book3);

        em.flush();
        em.clear();

        Boolean givenDigitalCopyRequired = true;
        PageRequest pageRequest = PageRequest.of(0, 5);
        BookSearchCriteria criteria = new BookSearchCriteria(null, null, null, null, null, null, null, null, null, givenDigitalCopyRequired);

        //when
        Page<Book> result = service.searchBooks(criteria, pageRequest);

        //then
        assertThat(result).hasSize(1);
        Book resultBook = result.getContent().getFirst();
        assertEquals(book, resultBook);
    }
}
