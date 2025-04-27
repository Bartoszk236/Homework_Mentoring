package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Address {
    private final String city;
    private final String zipCode;
    private final String street;
    private final String buildingNumber;
}
