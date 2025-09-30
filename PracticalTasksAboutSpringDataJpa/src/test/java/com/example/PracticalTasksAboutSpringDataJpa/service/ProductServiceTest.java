package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Product;
import com.example.PracticalTasksAboutSpringDataJpa.model.Category;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.PracticalTasksAboutSpringDataJpa.model.Category.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(ProductService.class)
class ProductServiceTest {
    @Autowired
    private ProductService service;
    @Autowired
    private TestEntityManager em;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 2; i++) {
            persistProduct(ELECTRONICS);
        }
        for (int i = 0; i < 7; i++) {
            persistProduct(FOOD);
        }
        em.flush();
        em.clear();
    }

    @Test
    void givenCategoryAndRequestFiveElementsOfPageWhenGetProductsPagesByCategoryThenReturnProductsPages() {
        //given
        Category givenCategory = FOOD;
        Pageable firstPageRequest = PageRequest.of(0, 5);
        Pageable secondPageRequest = PageRequest.of(1, 5);

        //when
        Page<Product> firstPageResult = service.getProductsPagesByCategory(givenCategory, firstPageRequest);
        Page<Product> secondPageResult = service.getProductsPagesByCategory(givenCategory, secondPageRequest);

        //then
        assertThat(firstPageResult).isNotEmpty();
        assertThat(firstPageResult).hasSize(5);
        assertTrue(firstPageResult
                .stream()
                .allMatch(product -> product.getCategory().equals(givenCategory))
        );

        assertThat(secondPageResult).isNotEmpty();
        assertThat(secondPageResult).hasSize(2);
        assertTrue(secondPageResult
                .stream()
                .allMatch(product -> product.getCategory().equals(givenCategory))
        );
    }

    private void persistProduct(Category category) {
        Product product = new Product();
        product.setName(faker.name().fullName());
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(faker.number().numberBetween(1,100)));
        product.setCreatedDate(LocalDate.now().minusDays(faker.number().numberBetween(1,100)));
        em.persist(product);
    }
}
