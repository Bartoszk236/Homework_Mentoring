package com.test;

import com.test.entity.Address;
import com.test.entity.Client;
import com.test.validator.ClientValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientValidatorTest {
    private final ClientValidator clientValidator = new ClientValidator();

    private Client validClient = new Client("Bartosz", "Kocyło",
            LocalDate.of(2003, 5, 24), "kocylo.bartosz@gmail.com",
            "123456789",
            new Address("Warszawa", "00-200", "Marszałkowska", "10")

    );

    @Test
    void givenValidClientWhenValidateThenReturnTrue() {
        //given
        Client givenClient = validClient;

        //when
        boolean result = clientValidator.validate(givenClient);

        //then
        assertTrue(result);
    }

    @Test
    void givenNullClientWhenValidateThenThrowIllegalArgumentException() {
        //given
        Client givenClient = null;

        //when/then
        assertThatThrownBy(() -> clientValidator.validate(givenClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("client is null");
    }

    @Test
    void givenClientWithNullFirstNameWhenValidateThenThrowInvalidArgumentException() {
        //given
        Client givenClient = validClient;
        givenClient.setFirstName(null);

        //when/then
        assertThatThrownBy(() -> clientValidator.validate(givenClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("First name is null");
    }

    @Test
    void givenClientWithNullLastNameWhenValidateThenThrowInvalidArgumentException() {
        //given
        Client givenClient = validClient;
        givenClient.setLastName(null);

        //when/then
        assertThatThrownBy(() -> clientValidator.validate(givenClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Last name is null");
    }

    @Test
    void givenClientWithNullPhoneWhenValidateThenThrowInvalidArgumentException() {
        //given
        Client givenClient = validClient;
        givenClient.setPhone(null);
        //when/then
        assertThatThrownBy(() -> clientValidator.validate(givenClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid phone number");
    }

    @Test
    void givenClientWithDifferentLengthPhoneWhenValidateThenThrowInvalidArgumentException() {
        //given
        Client givenClient = validClient;
        //other than 9 letters
        givenClient.setPhone("12345678");

        //when/then
        assertThatThrownBy(() -> clientValidator.validate(givenClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid phone number");
    }

    @Test
    void givenClientWithNullEmailWhenValidateThenThrowInvalidArgumentException() {
        //given
        Client givenClient = validClient;
        givenClient.setEmail(null);

        //when/then
        assertThatThrownBy(() -> clientValidator.validate(givenClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email");
    }

    @Test
    void givenClientWithInvalidEmailWhenValidateThenThrowInvalidArgumentException() {
        //given
        Client givenClient = validClient;
        //email without '@'
        givenClient.setEmail("invalidemail.com");

        //when/then
        assertThatThrownBy(() -> clientValidator.validate(givenClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email");
    }
}
