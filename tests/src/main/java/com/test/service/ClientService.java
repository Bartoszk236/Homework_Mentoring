package com.test.service;

import com.test.Repository;
import com.test.entity.Client;
import com.test.validator.ClientValidator;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class ClientService {
    private final ClientValidator validator;
    private final Repository repository;
    private final ExcelService excelService;

    public ClientService(ClientValidator validator, Repository repository, ExcelService excelService) {
        this.validator = validator;
        this.repository = repository;
        this.excelService = excelService;
    }

    public void saveClient(Client client) {
        if (validator.validate(client)) {
            repository.save(client);
            System.out.println("401 success");
        } else {
            System.out.println("404 failed");
        }
    }

    //invalid method with method chaining
    public String oldGetClientCityByEmail(String email) {
        return repository.findClientByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Client not found")).getAddress().getCity();
    }

    //refactored method
    public String getClientCityByEmail(String email) {
        return repository.findCityByEmail(email);
    }

    public List<Client> getClientsFromExcel(String directory) {
        File file = new File(directory);
        return excelService.parseExcel(file);
    }

    public boolean isAdult(Client client) {
        return !client.getDateOfBirth().isAfter(LocalDate.now().minusYears(18));
    }
}
