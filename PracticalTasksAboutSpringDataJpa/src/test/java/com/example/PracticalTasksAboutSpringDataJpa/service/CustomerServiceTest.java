package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Address;
import com.example.PracticalTasksAboutSpringDataJpa.entity.Customer;
import com.github.javafaker.Faker;
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
    private final Faker faker = new Faker();

    @Test
    void givenFirstNameAndCityWhenGetCustomersByFirstNameAndCityThenReturnListOfCustomers() {
        //given
        String givenCity = faker.address().city();
        String givenFirstName = faker.name().firstName();

        Address address = new Address();
        address.setCity(givenCity);

        Customer customer = new Customer();
        customer.setFirstName(givenFirstName);
        customer.setAddress(address);
        em.persist(customer);

        Address address2 = new Address();
        address2.setCity(faker.address().city());

        Customer customer2 = new Customer();
        customer2.setFirstName(faker.name().firstName());
        customer2.setAddress(address2);
        em.persist(customer2);

        Address address3 = new Address();
        address3.setCity(faker.address().city());

        Customer customer3 = new Customer();
        customer3.setFirstName(faker.name().firstName());
        customer3.setAddress(address3);
        em.persist(customer3);

        em.flush();
        em.clear();

        //when
        List<Customer> result = service.getCustomersByFirstNameAndCity(givenFirstName, givenFirstName);

        //then
        result.forEach(resultCustomer -> {
            assertEquals(givenFirstName, resultCustomer.getFirstName());
            assertEquals(givenCity, resultCustomer.getAddress().getCity());
        });
    }

    @Test
    void givenFirstNamePrefixWhenGetCustomersByFirstNamePrefixThenReturnCustomersList() {
        //given
        String givenFirstName = faker.name().firstName();
        String givenFirstNamePrefix = faker.name().prefix();

        Customer customer1 =  new Customer();
        customer1.setFirstName(givenFirstName);
        em.persist(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName(faker.name().firstName());
        em.persist(customer2);

        Customer customer3 = new Customer();
        customer3.setFirstName(faker.name().firstName());
        em.persist(customer3);

        em.flush();
        em.clear();

        //when
        List<Customer> result = service.getCustomersByFirstNamePrefix(givenFirstNamePrefix);

        //then
        assertTrue(result
                .stream()
                .allMatch(customer -> customer.getFirstName().startsWith(givenFirstNamePrefix))
        );
    }
}
