package com.example.OrderSystemBusinessLayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    @Setter
    private String name;

    @Column(name = "stock_quantity")
    @Setter
    private Integer stockQuantity;

    @Column(name = "price")
    @Setter
    private BigDecimal price;

    @Column(name = "identify_uuid", updatable = false, unique = true, nullable = false)
    private String identifyUuid = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(identifyUuid, product.identifyUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUuid);
    }

    void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}
