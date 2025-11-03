package com.example.OrderSystemBusinessLayer.repository;

import com.example.OrderSystemBusinessLayer.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems.product", "orderItems", "customer"})
    Optional<Order> findById(Long id);
}
