package com.example.PracticalTasksAboutSpringDataJpa.repository.projections;

public interface CustomerWithAddress extends CustomerSummary {
    AddressInfo getAddress();

    interface AddressInfo {
        String getCity();
        String getCountry();
    }
}
