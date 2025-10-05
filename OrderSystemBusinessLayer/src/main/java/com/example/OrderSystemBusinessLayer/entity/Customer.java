package com.example.OrderSystemBusinessLayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "first_name")
    @Setter
    private String firstName;

    @Column(name = "last_name")
    @Setter
    private String lastName;

    @Column(name = "email")
    @Setter
    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Order> orders = new HashSet<>();

    @Column(name = "identify_uuid", updatable = false, unique = true, nullable = false)
    private String identifyUuid = UUID.randomUUID().toString();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(identifyUuid, customer.identifyUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUuid);
    }

    void addOrder(Order order) {
        orders.add(order);
    }
}
