package src.design.patterns.strategy;

import java.math.BigDecimal;

public class Payment {
    private PaymentStrategy strategy;

    public Payment setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void pay(BigDecimal amount) {
        if (strategy == null) throw new IllegalStateException("Payment strategy not set");
        strategy.pay(amount);
    }
}
