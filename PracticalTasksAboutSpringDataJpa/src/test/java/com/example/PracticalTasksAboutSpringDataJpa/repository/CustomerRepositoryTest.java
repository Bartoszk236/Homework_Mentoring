package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Address;
import com.example.PracticalTasksAboutSpringDataJpa.entity.Customer;
import com.example.PracticalTasksAboutSpringDataJpa.repository.projections.CustomerDto;
import com.example.PracticalTasksAboutSpringDataJpa.repository.projections.CustomerSummary;
import com.example.PracticalTasksAboutSpringDataJpa.repository.projections.CustomerWithAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private TestEntityManager em;

    @Test
    void test_findAllCustomersSummary() {
        //given
        persistCustomerWithAddress();

        //when
        List<CustomerSummary> results = repository.findAllCustomersSummary();

        //then
        CustomerSummary resultsCustomerSummary = results.getFirst();
        assertEquals("Bartosz", resultsCustomerSummary.getFirstName());
        assertEquals("X", resultsCustomerSummary.getLastName());
        assertEquals("bartoszX@gmail.com", resultsCustomerSummary.getEmail());
    }

    @Test
    void test_findAllCustomersWithAddress() {
        //given
        persistCustomerWithAddress();

        //when
        List<CustomerWithAddress> results = repository.findAllCustomersWithAddress();

        //then
        CustomerWithAddress resultsCustomerWithAddress = results.getFirst();
        assertEquals("Bartosz", resultsCustomerWithAddress.getFirstName());
        assertEquals("X", resultsCustomerWithAddress.getLastName());
        assertEquals("bartoszX@gmail.com", resultsCustomerWithAddress.getEmail());

        CustomerWithAddress.AddressInfo resultsAddress =  resultsCustomerWithAddress.getAddress();
        assertEquals("Warszawa", resultsAddress.getCity());
        assertEquals("Polska", resultsAddress.getCountry());
    }

    @Test
    void test_findAllCustomersWithCity() {
        //given
        persistCustomerWithAddress();

        //when
        List<CustomerDto> results = repository.findAllCustomersWithCity();

        //then
        CustomerDto restulsCustomerDto = results.getFirst();
        assertEquals("Bartosz X", restulsCustomerDto.getFullName());
        assertEquals("bartoszX@gmail.com", restulsCustomerDto.getEmail());
        assertEquals("Warszawa", restulsCustomerDto.getCity());
    }

    private void persistCustomerWithAddress() {
        Address address = new Address();
        address.setCity("Warszawa");
        address.setCountry("Polska");

        Customer customer = new Customer();
        customer.setFirstName("Bartosz");
        customer.setLastName("X");
        customer.setEmail("bartoszX@gmail.com");
        customer.setAddress(address);

        em.persist(customer);
        em.flush();
        em.clear();
    }
}
