package com.test.validator;

import com.test.entity.Client;

public class ClientValidator {
    public boolean validate(Client client) {
        if (client == null) throw new IllegalArgumentException("client is null");
        if (client.getFirstName() == null) throw new IllegalArgumentException("First name is null");
        if (client.getLastName() == null) throw new IllegalArgumentException("Last name is null");
        if (client.getPhone() == null || client.getPhone().length() != 9) throw new IllegalArgumentException("Invalid phone number");
        if (client.getEmail() == null || !client.getEmail().contains("@")) throw new IllegalArgumentException("Invalid email");
        return true;
    }
}
