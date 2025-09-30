package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Address;
import com.example.PracticalTasksAboutSpringDataJpa.entity.Customer;
import com.example.PracticalTasksAboutSpringDataJpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomersByFirstNameAndCity(String firstName, String city) {
        Address probeAddress = new Address();
        probeAddress.setCity(city);

        Customer probeCustomer = new Customer();
        probeCustomer.setFirstName(firstName);
        probeCustomer.setAddress(probeAddress);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnorePaths("address.customer")
                .withStringMatcher(CONTAINING)
                .withIgnoreCase();

        Example<Customer> example = Example.of(probeCustomer, exampleMatcher);
        return customerRepository.findAll(example);
    }

    public List<Customer> getCustomersByFirstNamePrefix(String firstNamePrefix) {
        Customer probeCustomer = new Customer();
        probeCustomer.setFirstName(firstNamePrefix);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withStringMatcher(CONTAINING)
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatcher::startsWith);

        Example<Customer> example = Example.of(probeCustomer, exampleMatcher);
        return customerRepository.findAll(example);
    }
}
