package com.example.contoller.with.advanced.validation.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ISBNValidatorTest {
    private final ISBNValidator isbnValidator = new ISBNValidator();

    @Test
    void givenValidISBNWhenValidateThenReturnTrue() {
        //given
        String validISBN = "978-3-16-148410-0";

        //when
        boolean result = isbnValidator.isValid(validISBN, null);

        //then
        assertTrue(result);
    }

    @Test
    void givenInvalidISBNWhenValidateThenReturnFalse() {
        //given
        String invalidISBN = "978-3-16-148410-0A";

        //when
        boolean result = isbnValidator.isValid(invalidISBN, null);

        //then
        assertFalse(result);
    }
}
