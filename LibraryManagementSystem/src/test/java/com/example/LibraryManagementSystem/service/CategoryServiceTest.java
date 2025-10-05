package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.SampleDataBuilder;
import com.example.LibraryManagementSystem.dto.CategoryWithAvgPages;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Category;
import com.example.LibraryManagementSystem.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import({
        CategoryService.class,
        SampleDataBuilder.class
})
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SampleDataBuilder sampleDataBuilder;

    @BeforeEach
    void setUp() {
        sampleDataBuilder.initSampleData();
    }

    @Test
    void givenCategoriesAndBooksWhenGetCategoriesWithAvgPagesThenReturnObjectAndValueList() {
        //given
        List<Category> category = categoryRepository.findAll();

        List<CategoryWithAvgPages> expected = calculateAvgPagesByCategory(category);

        //when
        List<CategoryWithAvgPages> result = categoryService.getCategoriesWithAvgPages();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    private List<CategoryWithAvgPages> calculateAvgPagesByCategory(List<Category> categories) {
        List<CategoryWithAvgPages> result = new ArrayList<>();
        categories.forEach(category -> {
            Set<Book> books = category.getBooks();
            int avg = books.isEmpty()
                    ? 0
                    : (int) Math.round(books.stream()
                    .mapToInt(Book::getPagesNumber)
                    .average()
                    .orElse(0.0));

            result.add(new CategoryWithAvgPages(category, avg));
        });
        return result;
    }
}
