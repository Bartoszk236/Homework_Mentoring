package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Product;
import com.example.PracticalTasksAboutSpringDataJpa.model.Category;
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

    @BeforeEach
    void setUp() {
        persistProduct("Laptop X200", ELECTRONICS, "3499.00", LocalDate.of(2024, 1, 15));
        persistProduct("Smartphone A1", ELECTRONICS, "1999.99", LocalDate.of(2024, 3, 5));
        persistProduct("Headphones Pro", ELECTRONICS, "499.50", LocalDate.of(2024, 2, 12));

        persistProduct("Organic Pasta", FOOD, "8.99", LocalDate.of(2024, 5, 1));
        persistProduct("Dark Chocolate 70%", FOOD, "5.49", LocalDate.of(2024, 5, 10));
        persistProduct("Arabica Coffee 1kg", FOOD, "49.90", LocalDate.of(2024, 4, 21));
        persistProduct("Extra Virgin Olive Oil", FOOD, "34.90", LocalDate.of(2024, 6, 11));
        persistProduct("Jasmine Rice 5kg", FOOD, "39.99", LocalDate.of(2024, 7, 3));
        persistProduct("Peanut Butter", FOOD, "14.50", LocalDate.of(2024, 4, 8));
        persistProduct("Green Tea 50 bags", FOOD, "12.20", LocalDate.of(2024, 8, 18));

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

    private void persistProduct(String name, Category category, String price, LocalDate createdDate) {
        Product p = new Product();
        p.setName(name);
        p.setCategory(category);
        p.setPrice(new BigDecimal(price));
        p.setCreatedDate(createdDate);
        em.persist(p);
    }
}
