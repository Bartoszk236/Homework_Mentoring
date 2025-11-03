package com.example.OrderSystemBusinessLayer.repository;

import com.example.OrderSystemBusinessLayer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
