package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Product;
import com.example.PracticalTasksAboutSpringDataJpa.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static com.example.PracticalTasksAboutSpringDataJpa.model.Category.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private TestEntityManager em;

    @Test
    void test_findByCategoryOrderByPriceAsc() {
        //given
        em.persist(createProduct("Product 1", FOOD, "9.99"));
        em.persist(createProduct("Product 2", FOOD, "5.99"));
        em.persist(createProduct("Product 3", FOOD, "6.99"));
        em.persist(createProduct("Product 4", FOOD, "8.99"));
        em.persist(createProduct("Product 5", ELECTRONICS, "9.99"));

        em.flush();
        em.clear();

        //when
        List<Product> result = repository.findByCategoryOrderByPriceAsc(FOOD);

        //then
        result.forEach(product -> assertEquals(FOOD, product.getCategory()));

        List<Product> resultSorted = result.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();

        assertThat(result).containsExactlyElementsOf(resultSorted);
    }

    private Product createProduct(String name, Category category, String price) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(new BigDecimal(price));
        return product;
    }
}
