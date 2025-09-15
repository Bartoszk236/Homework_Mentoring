package com.example.PracticalTasksAboutSpringDataJpa.repository.projections;

public interface CustomerWithAddress {
    String getFirstName();
    String getLastName();
    String getEmail();
    AddressInfo getAddress();

    interface AddressInfo {
        String getCity();
        String getCountry();
    }
}
