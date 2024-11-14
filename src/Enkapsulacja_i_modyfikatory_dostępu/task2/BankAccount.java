package src.Enkapsulacja_i_modyfikatory_dostępu.task2;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BankAccount {
    private final BigInteger accountNumber;
    private BigDecimal balance;

    public BankAccount(BigInteger accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public BankAccount deposit(BigDecimal depositAmount) {
        this.balance = balance.add(depositAmount);
        return this;
    }

    public BankAccount withdraw(BigDecimal withdrawAmount) {
        if (balance.doubleValue() < withdrawAmount.doubleValue()) throw new IllegalArgumentException("Insufficient funds");
        this.balance = balance.subtract(withdrawAmount);
        return this;
    }

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
