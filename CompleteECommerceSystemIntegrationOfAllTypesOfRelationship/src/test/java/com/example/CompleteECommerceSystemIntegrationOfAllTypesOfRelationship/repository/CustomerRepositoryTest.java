package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.EntityInitialization;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({EntityInitialization.class})
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private EntityInitialization entityInitialization;
    private EntityInitialization.SeededData seededData;

    @BeforeEach
    public void setup() {
        seededData = entityInitialization.seedFull();
    }

    @Test
    void findInactiveCustomers_test() {
        Customer expectedCustomer = seededData.customers()
                .stream()
                .filter(customer -> customer.getProfile().getFirstName().equals("Alice"))
                .findFirst()
                .orElseThrow();

        //when
        List<Customer> results = repository.findInactiveCustomers(LocalDateTime.now().minusDays(1).minusHours(12));

        //then
        assertEquals(1, results.size());
        assertEquals(expectedCustomer, results.getFirst());
    }
}
