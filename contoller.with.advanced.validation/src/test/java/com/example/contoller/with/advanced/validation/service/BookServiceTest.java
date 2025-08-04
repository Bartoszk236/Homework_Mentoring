package com.example.contoller.with.advanced.validation.service;

import com.example.contoller.with.advanced.validation.dto.BookRequest;
import com.example.contoller.with.advanced.validation.entity.Book;
import com.example.contoller.with.advanced.validation.mapper.BookMapper;
import com.example.contoller.with.advanced.validation.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {
    private final BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private final BookMapper bookMapper = Mockito.mock(BookMapper.class);
    private final BookService bookService = new BookService(bookRepository, bookMapper);

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
            .title("Harry Potter")
            .author("John Doe")
            .isbn("123-4567-89012-3")
            .price(new BigDecimal("50.00"))
            .publishDate(LocalDate.of(2021, 3, 10))
            .description("Harry Potter is a good book")
            .build();

    private final Book validBook2 = Book.builder()
            .bookId(2L)
            .title("Harry Potter2")
            .author("John Doe2")
            .isbn("123-4567-89012-3")
            .price(new BigDecimal("51.00"))
            .publishDate(LocalDate.of(2021, 3, 11))
            .description("Harry Potter is a good book2")
            .build();

    @Test
    void givenRequestWhenSaveThenCallRepositoryAndMapperMethods() {
        //give
        when(bookMapper.toBook(validBookRequest)).thenReturn(validBook);

        //when
        bookService.createBook(validBookRequest);

        //then
        verify(bookMapper).toBook(validBookRequest);
        verify(bookRepository).save(validBook);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void givenRequestWhenUpdateThenCallRepositoryAndMapperMethods() {
        //given
        Long id = 1L;
        when(bookRepository.findById(1L)).thenReturn(Optional.of(validBook));
        when(bookMapper.toBook(validBook, validBookRequest)).thenReturn(validBook);

        //when
        bookService.updateBook(id, validBookRequest);

        //then
        verify(bookRepository).findById(id);
        verify(bookMapper).toBook(validBook, validBookRequest);
        verify(bookRepository).save(validBook);
    }

    @Test
    void givenInvalidBookIdWhenUpdateThenThrowException() {
        //given
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        //when/then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookService.updateBook(id, validBookRequest));
        assertEquals("book not found", ex.getMessage());
        verify(bookRepository).findById(id);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void givenBookIdWhenDeleteThenCallRepositoryMethods() {
        //given
        Long id = 1L;

        //when
        bookService.deleteBook(id);

        //then
        verify(bookRepository).deleteById(id);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void givenValidDataWhenGetAllThenReturnPage() {
        //given
        int page = 1, size = 10;
        String sortBy = "title";
        boolean asc = false;

        List<Book> content = List.of(
                validBook, validBook2
        );
        Page<Book> pageResult = new PageImpl<>(content);
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

        //when
        Page<Book> result = bookService.getAll(page, size, sortBy, asc);

        //then
        assertSame(pageResult, result);
        verify(bookRepository).findAll(captor.capture());

        Pageable pageable = captor.getValue();
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());
        assertEquals(Sort.Direction.DESC, pageable.getSort().getOrderFor(sortBy).getDirection());
    }
}
