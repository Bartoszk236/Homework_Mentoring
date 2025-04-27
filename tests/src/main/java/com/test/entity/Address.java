package com.test.entity;

public class Address {
    private final String city;
    private final String zipCode;
    private final String street;
    private final String buildingNumber;

    public Address(String city, String zipCode, String street, String buildingNumber) {
        this.buildingNumber = buildingNumber;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }
}
