package com.example.contoller.with.advanced.validation.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthorValidatorTest {
    private final AuthorValidator authorValidator = new AuthorValidator();

    @Test
    void givenValidAuthorWhenValidatingThenReturnTrue() {
        //given
        String validAuthor = "John Doe";

        //when
        boolean result = authorValidator.isValid(validAuthor, null);

        //then
        assertTrue(result);
    }

    @Test
    void givenInvalidAuthorWhenValidatingThenReturnFalse() {
        //given
        String invalidAuthor = "John ";

        //when
        boolean result = authorValidator.isValid(invalidAuthor, null);

        //then
        assertFalse(result);
    }
}
