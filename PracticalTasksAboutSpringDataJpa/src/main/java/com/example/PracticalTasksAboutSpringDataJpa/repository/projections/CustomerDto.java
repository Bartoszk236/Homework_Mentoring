package com.example.PracticalTasksAboutSpringDataJpa.repository.projections;

import lombok.Getter;

@Getter
public class CustomerDto {
    private final String fullName;
    private final String email;
    private final String city;

    public CustomerDto(String firstName, String lastName, String email, String city) {
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.city = city;
    }
}
