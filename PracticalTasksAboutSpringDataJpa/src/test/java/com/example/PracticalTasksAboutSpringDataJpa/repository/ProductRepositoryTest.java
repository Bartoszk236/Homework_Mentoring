package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.LinkedList;
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
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setName("Product 1");
        product1.setCategory(FOOD);
        product1.setPrice(new BigDecimal("9.99"));
        em.persist(product1);

        product2 = new Product();
        product2.setName("Product 2");
        product2.setCategory(FOOD);
        product2.setPrice(new BigDecimal("5.99"));
        em.persist(product2);

        product3 = new Product();
        product3.setName("Product 3");
        product3.setCategory(FOOD);
        product3.setPrice(new BigDecimal("6.99"));
        em.persist(product3);

        product4 = new Product();
        product4.setName("Product 4");
        product4.setCategory(ELECTRONICS);
        product4.setPrice(new BigDecimal("8.99"));
        em.persist(product4);

        product5 = new Product();
        product5.setName("Product 5");
        product5.setCategory(ELECTRONICS);
        product5.setPrice(new BigDecimal("9.99"));
        em.persist(product5);

        em.flush();
        em.clear();
    }

    @Test
    void test_findByCategoryOrderByPriceAsc() {
        //given
        LinkedList<Product> expectedProducts = new LinkedList<>();
        expectedProducts.add(product2);
        expectedProducts.add(product3);
        expectedProducts.add(product1);

        //when
        List<Product> result = repository.findByCategoryOrderByPriceAsc(FOOD);

        //then
        assertThat(result).containsExactlyElementsOf(expectedProducts);
        assertThat(result).hasSize(3);
        result.forEach(product -> assertEquals(FOOD, product.getCategory()));
    }
}
