package src.practical.application.of.abstraction.task25;

import java.math.BigDecimal;

public class CashPayment extends Payment {
    private BigDecimal cash;

    public CashPayment(BigDecimal amount, BigDecimal cash) {
        super(amount);
        this.cash = cash;
    }

    @Override
    void processPayment() {
        System.out.println("Give a change for client: " + cash.subtract(getAmount()) + "$");
    }
}
