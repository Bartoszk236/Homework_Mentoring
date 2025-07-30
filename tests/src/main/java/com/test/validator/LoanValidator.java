package com.test.validator;

import com.test.entity.Loan;

public class LoanValidator {
    public boolean checkLoanType(String newType) {
        if (newType == null) throw new IllegalArgumentException("loan type is null");
        if (newType.length() < 10) throw new IllegalArgumentException("type of loan is less than 10");
        return true;
    }

    public void check(Loan loan) {
        System.out.println("Success");
    }
}
