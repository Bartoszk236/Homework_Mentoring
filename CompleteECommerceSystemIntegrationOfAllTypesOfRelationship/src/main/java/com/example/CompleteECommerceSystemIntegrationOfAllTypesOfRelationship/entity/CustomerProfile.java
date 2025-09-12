package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_profile")
@Getter
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_profile_id")
    private Long id;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
