package com.test;

import com.test.validator.LoanValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LoanValidatorTest {
    private final LoanValidator validator;

    LoanValidatorTest() {
        this.validator = new LoanValidator();
    }

    @Test
    void givenValidNewTypeWhenValidNewTypeThenReturnTrue() {
        //given
        String validType = "Express cash credit";

        //when
        boolean result = validator.checkLoanType(validType);

        //then
        assert result;
    }

    @Test
    void givenNullNewTypeWhenValidNewTypeThenReturnFalse() {
        //given
        String newType = null;

        //when/then
        assertThatThrownBy(() -> validator.checkLoanType(newType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("loan type is null");
    }

    @Test
    void givenNewTypeWithLessThen10LettersWhenValidNewTypeThenReturnFalse() {
        //given
        String newType = "to short";

        //when/then
        assertThatThrownBy(() -> validator.checkLoanType(newType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("type of loan is less than 10");
    }
}
