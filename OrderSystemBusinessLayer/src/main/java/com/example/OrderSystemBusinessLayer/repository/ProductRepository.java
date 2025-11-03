package com.example.OrderSystemBusinessLayer.repository;

import com.example.OrderSystemBusinessLayer.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
