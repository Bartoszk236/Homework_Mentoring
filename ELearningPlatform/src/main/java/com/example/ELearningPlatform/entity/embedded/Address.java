package com.example.ELearningPlatform.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    @Column(name = "address_city")
    private String city;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_building_number")
    private String buildingNumber;

    @Column(name = "address_apartment_number")
    private String apartmentNumber;

    @Column(name = "address_zipcode")
    private String zipcode;

    @Column(name = "address_country")
    private String country;
}
