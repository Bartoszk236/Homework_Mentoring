package src.practical.application.of.abstraction.task25;

import java.math.BigDecimal;

abstract public class Payment {
    private BigDecimal amount;

    public Payment(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    abstract void processPayment();
}