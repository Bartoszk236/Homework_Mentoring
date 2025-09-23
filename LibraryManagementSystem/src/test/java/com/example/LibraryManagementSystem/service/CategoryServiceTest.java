package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.CategoryWithAvgPages;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import(CategoryService.class)
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TestEntityManager em;

    @Test
    void givenCategoriesAndBooksWhenGetCategoriesWithAvgPagesThenReturnObjectAndValueList() {
        //given
        Category category1 = new Category();
        category1.setName("Category 1");
        em.persist(category1);
        Category category2 = new Category();
        category2.setName("Category 2");
        em.persist(category2);

        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setIsbn("0000000000000");
        book1.setPagesNumber(212);
        book1.setCategory(category1);
        em.persist(book1);

        Book book2 = new Book();
        book2.setTitle("War of Aliens");
        book2.setIsbn("0000000000001");
        book2.setPagesNumber(321);
        book2.setCategory(category1);
        em.persist(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter2");
        book3.setIsbn("0000000000002");
        book3.setPagesNumber(400);
        book3.setCategory(category2);
        em.persist(book3);

        Book book4 = new Book();
        book4.setTitle("Harry Potter2");
        book4.setIsbn("0000000000003");
        book4.setPagesNumber(500);
        book4.setCategory(category2);
        em.persist(book4);

        em.flush();
        em.clear();

        List<CategoryWithAvgPages> expected = List.of(
                new CategoryWithAvgPages(category1, 266.5),
                new CategoryWithAvgPages(category2, 450.0)
        );

        //when
        List<CategoryWithAvgPages> result = categoryService.getCategoriesWithAvgPages();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }
}
