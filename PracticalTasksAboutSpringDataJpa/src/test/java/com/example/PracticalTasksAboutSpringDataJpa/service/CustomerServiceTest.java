package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Address;
import com.example.PracticalTasksAboutSpringDataJpa.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(CustomerService.class)
class CustomerServiceTest {
    @Autowired
    private CustomerService service;
    @Autowired
    private TestEntityManager em;

    @Test
    void givenFirstNameAndCityWhenGetCustomersByFirstNameAndCityThenReturnListOfCustomers() {
        //given
        Address address = new Address();
        address.setCity("Warszawa");
        Customer customer = new Customer();
        customer.setFirstName("Bartosz");
        customer.setAddress(address);
        em.persist(customer);

        Address address2 = new Address();
        address2.setCity("Berlin");
        Customer customer2 = new Customer();
        customer2.setFirstName("Bartosz");
        customer2.setAddress(address2);
        em.persist(customer2);

        Address address3 = new Address();
        address3.setCity("Warszawa");
        Customer customer3 = new Customer();
        customer3.setFirstName("Jakub");
        customer3.setAddress(address3);
        em.persist(customer3);

        em.flush();
        em.clear();

        //when
        List<Customer> result = service.getCustomersByFirstNameAndCity("bartosz", "warszawa");

        //then
        assertThat(result).hasSize(1);

        Customer resultCustomer = result.getFirst();
        assertEquals("Bartosz", resultCustomer.getFirstName());
        assertEquals("Warszawa", resultCustomer.getAddress().getCity());
    }

    @Test
    void givenFirstNamePrefixWhenGetCustomersByFirstNamePrefixThenReturnCustomersList() {
        //given
        String firstNamePrefix = "Bar";

        Customer customer1 =  new Customer();
        customer1.setFirstName("Bartosz");
        em.persist(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Barbara");
        em.persist(customer2);

        Customer customer3 = new Customer();
        customer3.setFirstName("Jakub");
        em.persist(customer3);

        em.flush();
        em.clear();

        //when
        List<Customer> result = service.getCustomersByFirstNamePrefix(firstNamePrefix);

        //then
        assertThat(result).hasSize(2);
        assertTrue(result
                .stream()
                .allMatch(customer -> customer.getFirstName().startsWith(firstNamePrefix))
        );
    }
}
