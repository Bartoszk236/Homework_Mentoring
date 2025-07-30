package com.test;

import com.test.entity.Address;
import com.test.entity.Client;
import com.test.validator.ClientValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientValidatorTest {
    private static final Class<IllegalArgumentException> EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION = IllegalArgumentException.class;
    private final ClientValidator clientValidator = new ClientValidator();

    private static Stream<Arguments> dataForTestingValidateMethod() {

        return Stream.of(
                Arguments.of(null, EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION, "client is null", "givenNullClient"),
                Arguments.of(
                        new Client(null, "Kocyło",
                                LocalDate.of(2003, 5, 24), "kocylo.bartosz@gmail.com",
                                "123456789",
                                new Address("Warszawa", "00-200", "Marszałkowska", "10")
                        ),
                        EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION,
                        "First name is null",
                        "givenNullFirstName"
                ),
                Arguments.of(
                        new Client("Bartosz", null,
                                LocalDate.of(2003, 5, 24), "kocylo.bartosz@gmail.com",
                                "123456789",
                                new Address("Warszawa", "00-200", "Marszałkowska", "10")
                        ),
                        EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION,
                        "Last name is null",
                        "givenNullLastName"
                ),
                Arguments.of(
                        new Client("Bartosz", "Kocyło",
                                LocalDate.of(2003, 5, 24), "kocylo.bartosz@gmail.com",
                                null,
                                new Address("Warszawa", "00-200", "Marszałkowska", "10")
                        ),
                        EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION,
                        "Invalid phone number",
                        "givenNullPhone"
                ),
                Arguments.of(
                        new Client("Bartosz", "Kocyło",
                                LocalDate.of(2003, 5, 24), "kocylo.bartosz@gmail.com",
                                "12345678",
                                new Address("Warszawa", "00-200", "Marszałkowska", "10")
                        ),
                        EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION,
                        "Invalid phone number",
                        "givenNullPhoneDifferentThanNineLetters"
                ),
                Arguments.of(
                        new Client("Bartosz", "Kocyło",
                                LocalDate.of(2003, 5, 24), null,
                                "123456789",
                                new Address("Warszawa", "00-200", "Marszałkowska", "10")
                        ),
                        EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION,
                        "Invalid email",
                        "givenNullEmail"
                ),
                Arguments.of(
                        new Client("Bartosz", "Kocyło",
                                LocalDate.of(2003, 5, 24), "invalidemail.com",
                                "123456789",
                                new Address("Warszawa", "00-200", "Marszałkowska", "10")
                        ),
                        EXPECTED_ILLEGAL_ARGUMENT_EXCEPTION,
                        "Invalid email",
                        "givenNullInvalidEmailWithout@char"
                )
        );
    }

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

    @ParameterizedTest(name = "{3}")
    @MethodSource("dataForTestingValidateMethod")
    void givenInvalidClientWhenValidateThenThrowsException(Client invalidClient, Class<? extends Exception> expectedException, String expectedMessage, String description) {
        //when/then
        assertThatThrownBy(() -> clientValidator.validate(invalidClient))
                .isInstanceOf(expectedException)
                .hasMessage(expectedMessage);
    }
}
