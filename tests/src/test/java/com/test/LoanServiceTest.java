package com.test;

import com.test.entity.Discount;
import com.test.entity.Loan;
import com.test.service.LoanService;
import com.test.validator.LoanValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class LoanServiceTest {
    private final Repository repository;
    private final LoanValidator validator;
    private final LoanService loanService;

    LoanServiceTest() {
        this.repository = mock(Repository.class);
        this.validator = mock(LoanValidator.class);
        this.loanService = new LoanService(repository, validator);
    }

    Loan validLoan = new Loan(new BigDecimal("5.5"), new BigDecimal("50000"), "Cash credit");
    Discount validDiscount = new Discount("New user discount", new BigDecimal("0.5"));

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

    @Test
    void givenNullLoanObjectWhenAddDiscountThenReturnIllegalArgumentException() {
        //given
        Loan givenLoan = null;
        Discount givenDiscount = validDiscount;

        //when/then
        assertThatThrownBy(() -> loanService.addDiscount(givenLoan, givenDiscount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("loan is null");
    }

    @Test
    void givenNullDiscountObjectWhenAddDiscountThenReturnIllegalArgumentException() {
        //given
        Loan givenLoan = validLoan;
        Discount givenDiscount = null;

        //when/then
        assertThatThrownBy(() -> loanService.addDiscount(givenLoan, givenDiscount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("discount is null");
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
