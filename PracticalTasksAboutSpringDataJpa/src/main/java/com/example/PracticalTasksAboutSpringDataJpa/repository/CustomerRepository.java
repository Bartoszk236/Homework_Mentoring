package com.example.PracticalTasksAboutSpringDataJpa.repository;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Customer;
import com.example.PracticalTasksAboutSpringDataJpa.repository.projections.CustomerDto;
import com.example.PracticalTasksAboutSpringDataJpa.repository.projections.CustomerSummary;
import com.example.PracticalTasksAboutSpringDataJpa.repository.projections.CustomerWithAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("""
            select c
            from Customer c
            """)
    List<CustomerSummary> findAllCustomersSummary();

    @Query("""
            select c
            from Customer c
            """)
    List<CustomerWithAddress> findAllCustomersWithAddress();
}
