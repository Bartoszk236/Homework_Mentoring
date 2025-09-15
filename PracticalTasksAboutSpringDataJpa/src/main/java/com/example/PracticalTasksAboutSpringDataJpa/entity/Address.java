package com.example.PracticalTasksAboutSpringDataJpa.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Setter
    @Column(name = "street")
    private String street;

    @Setter
    @Column(name = "building_number")
    private String buildingNumber;

    @Setter
    @Column(name = "city")
    private String city;

    @Setter
    @Column(name = "country")
    private String country;

    @OneToOne(mappedBy = "address")
    private Customer customer;

    void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
