package com.test.entity;

import java.math.BigDecimal;

public class Loan {
    private final BigDecimal loanAmount;
    private String loanType;
    private final BigDecimal interestRate;
    private Discount discount;

    public Loan(BigDecimal interestRate, BigDecimal loanAmount, String loanType) {
        this.interestRate = interestRate;
        this.loanAmount = loanAmount;
        this.loanType = loanType;
    }

    public Discount getDiscount() {
        return discount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public String getLoanType() {
        return loanType;
    }

    public Loan setDiscount(Discount discount) {
        this.discount = discount;
        return this;
    }

    public Loan setLoanType(String loanType) {
        this.loanType = loanType;
        return this;
    }
}
