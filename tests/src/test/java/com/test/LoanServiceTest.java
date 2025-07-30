package com.test;

import com.test.entity.Discount;
import com.test.entity.Loan;
import com.test.service.LoanService;
import com.test.validator.LoanValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class LoanServiceTest {
    private Repository repository = mock(Repository.class);
    private LoanValidator validator = mock(LoanValidator.class);
    private LoanService loanService = new LoanService(repository, validator);

    private static Stream<Arguments> dataForTestingAddDiscountMethod() {
        return Stream.of(
                Arguments.of(null,
                        new Discount("New user discount", new BigDecimal("0.5")),
                        IllegalArgumentException.class,
                        "loan is null",
                        "givenNullLoan"
                ),
                Arguments.of(Loan.builder()
                                .loanAmount(new BigDecimal("50000"))
                                .interestRate(new BigDecimal("5.5"))
                                .loanType("Cash credit")
                                .build(), null,
                        IllegalArgumentException.class,
                        "discount is null",
                        "givenNullDiscount"
                )
        );
    }

    private Discount validDiscount = new Discount("New user discount", new BigDecimal("0.5"));
    Loan validLoan = Loan.builder()
            .loanAmount(new BigDecimal("50000"))
            .interestRate(new BigDecimal("5.5"))
            .loanType("Cash credit")
            .build();

    @Test
    void givenValidDataWhenAddDiscountThenReturnLoanWithDiscount() {
        //given
        Loan givenLoan = validLoan;
        Discount givenDiscount = validDiscount;

        //when
        Loan resultLoan = loanService.addDiscount(givenLoan, givenDiscount);
        Discount resultDiscount = resultLoan.getDiscount();

        //then
        assertThat(resultDiscount)
                .isEqualTo(givenDiscount);
    }

    @ParameterizedTest(name = "{4}")
    @MethodSource("dataForTestingAddDiscountMethod")
    void givenInvalidDataWhenAddDiscountThenThrowsException(
            Loan loan, Discount discount, Class<? extends Exception> expectedException,
            String expectedMessage, String description) {
        //when/then
        assertThatThrownBy(() -> loanService.addDiscount(loan, discount))
                .isInstanceOf(expectedException)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    void givenValidNewLoanTypeWhenEditTypeOfLoanThenReturnLoanWithNewType() {
        //given
        Loan loan = validLoan;
        String givenLoanType = "Express cash credit";

        //when
        when(validator.checkLoanType(givenLoanType))
                .thenReturn(true);
        loanService.editTypeOfLoan(loan, givenLoanType);
        String result = loan.getLoanType();

        //then
        assertThat(result).isEqualTo(givenLoanType);
    }

    @Test
    void givenNullLoanWhenSaveThenReturnIllegalArgumentException() {
        //given
        Loan givenLoan = null;

        //when/then
        assertThatThrownBy(() -> loanService.saveInDataBase(givenLoan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("loan is null");
    }

    @Test
    void givenValidLoanWhenSaveThenRepositorySaveIsCalled() {
        //given
        Loan loan = validLoan;

        //when
        loanService.saveInDataBase(loan);

        //then
        verify(repository).save(loan);
    }

    @Test
    void givenLoanWhenCheckTwoTimesThenReturnLoanCheckTwoTimes() {
        //given
        Loan loan = validLoan;

        //when
        loanService.checkTwoTimes(loan);

        //then
        verify(validator, times(2)).check(loan);
    }
}
