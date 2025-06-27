package com.example.contoller.with.advanced.validation.mapper;

import com.example.contoller.with.advanced.validation.dto.BookRequest;
import com.example.contoller.with.advanced.validation.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookMapperTest {
    private final BookMapper bookMapper = new BookMapper();

    private final BookRequest validBookRequest = BookRequest.builder()
            .title("Harry Potter")
            .author("John Doe")
            .isbn("123-4567-89012-3")
            .price(new BigDecimal("50.00"))
            .publishDate(LocalDate.of(2021, 3, 10))
            .description("Harry Potter is a good book")
            .build();

    private final Book validBook = Book.builder()
            .bookId(1L)
            .title("Harry Potter2")
            .author("John Doe2")
            .isbn("123-4567-89012-3")
            .price(new BigDecimal("52.00"))
            .publishDate(LocalDate.of(2021, 3, 12))
            .description("Harry Potter is a good book2")
            .build();

    @Test
    void givenValidBookRequestAndOldBookWhenMapToBookThenReturnBook() {
        //give
        Book expected = Book.builder()
                .bookId(1L)
                .title("Harry Potter")
                .author("John Doe")
                .isbn("123-4567-89012-3")
                .price(new BigDecimal("50.00"))
                .publishDate(LocalDate.of(2021, 3, 10))
                .description("Harry Potter is a good book")
                .build();

        //when
        Book result = bookMapper.toBook(validBook, validBookRequest);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenValidBookRequestWhenMapToBookThenReturnBook() {
        //give
        Book expected = Book.builder()
                .title("Harry Potter")
                .author("John Doe")
                .isbn("123-4567-89012-3")
                .price(new BigDecimal("50.00"))
                .publishDate(LocalDate.of(2021, 3, 10))
                .description("Harry Potter is a good book")
                .build();

        //when
        Book result = bookMapper.toBook(validBookRequest);

        //then
        assertThat(result).isEqualTo(expected);
    }
}
