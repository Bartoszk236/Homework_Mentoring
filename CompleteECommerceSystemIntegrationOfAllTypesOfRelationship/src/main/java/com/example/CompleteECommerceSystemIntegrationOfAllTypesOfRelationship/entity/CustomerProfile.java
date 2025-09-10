package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "customer_profile")
@Getter
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_profile_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public CustomerProfile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerProfile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
