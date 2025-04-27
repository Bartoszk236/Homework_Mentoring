package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Loan {
    private final BigDecimal loanAmount;
    private String loanType;
    private final BigDecimal interestRate;
    private Discount discount;
}
