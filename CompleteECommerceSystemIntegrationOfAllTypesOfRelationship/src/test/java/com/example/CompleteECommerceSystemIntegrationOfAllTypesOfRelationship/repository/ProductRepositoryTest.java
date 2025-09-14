package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.EntityInitialization;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({EntityInitialization.class})
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private EntityInitialization entityInitialization;
    private EntityInitialization.SeededData seededData;

    @BeforeEach
    public void setup() {
        seededData = entityInitialization.seedFull();
    }

    @Test
    void findTopSellingProducts_test() {
        //given
        Product expectedProduct = seededData.products()
                .stream()
                .filter(product -> product.getName().equals("Laptop Pro 14"))
                .findFirst()
                .orElseThrow(AssertionError::new);

        Long expectedQuantity = 1L;

        //when
        List<Object[]> resultsList = repository.findTopSellingProducts(Pageable.ofSize(10));

        //then
        Object[] results = resultsList.getFirst();
        assertEquals(expectedProduct, results[0]);
        assertEquals(expectedQuantity, results[1]);
    }
}
