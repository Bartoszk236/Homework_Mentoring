package com.test.service;

import com.test.validator.LoanValidator;
import com.test.Repository;
import com.test.entity.Discount;
import com.test.entity.Loan;

public class LoanService {
    private final Repository repository;
    private final LoanValidator validator;

    public LoanService(Repository repository, LoanValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Loan addDiscount(Loan loan, Discount discount) {
        if (loan == null) throw new IllegalArgumentException("loan is null");
        if (discount == null) throw new IllegalArgumentException("discount is null");
        return loan.setDiscount(discount);
    }

    public void editTypeOfLoan(Loan loan, String newType) {
        if (validator.checkLoanType(newType)) {
            loan.setLoanType(newType);
        }
    }

    public void saveInDataBase(Loan loan) {
        if (loan == null) throw new IllegalArgumentException("loan is null");
        repository.save(loan);
    }

    public void checkTwoTimes(Loan loan) {
        validator.check(loan);
        validator.check(loan);
    }

    //old private method
    private boolean checkLoanType(String newType) {
        if (newType == null) throw new IllegalArgumentException("type is null");
        if (newType.length() < 10) throw new IllegalArgumentException("type of loan is less than 10");
        return true;
    }
}
