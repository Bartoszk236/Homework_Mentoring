package com.test;

import com.test.entity.Client;
import com.test.entity.Loan;

import java.util.Optional;

public interface Repository {
    void save(Loan loan);
    void save(Client client);
    Optional<Client> findClientByEmail(String email);
    default String findCityByEmail(String email) {
        return findClientByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"))
                .getCityName();
    }
}
