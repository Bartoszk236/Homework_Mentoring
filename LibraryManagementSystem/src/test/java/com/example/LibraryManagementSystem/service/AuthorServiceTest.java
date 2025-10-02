package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.SampleDataBuilder;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import({
        AuthorService.class,
        SampleDataBuilder.class
})
class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private SampleDataBuilder sampleDataBuilder;
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        sampleDataBuilder.initSampleData();
    }

    @Test
    void givenFewAuthorsWithBooksWithBorrowsWhenGetAuthorsByMostBorrowCountsThenReturnPageWithBorrowsCountDescOrder() {
        //given
        int expectedSize = 3;

        List<Author> expectedOrder = authorRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(this::calculateBorrowsCount).reversed()
                        .thenComparing(Comparator.comparing(Author::getId).reversed())
                )
                .limit(expectedSize)
                .toList();

        //when
        Page<Author> result = authorService.getAuthorsByMostBorrowCounts(0, expectedSize);

        //then
        assertThat(result).hasSize(expectedSize);
        assertThat(result.getContent()).containsExactlyElementsOf(expectedOrder);
    }

    private long calculateBorrowsCount(Author author) {
        return author.getBooks()
                .stream()
                .map(Book::getBorrowRecords)
                .mapToLong(Set::size)
                .sum();
    }
}
