package src.design.patterns.strategy;

import java.math.BigDecimal;

public class VisaPayment implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount) {
        System.out.println("Zapłacono " + amount + " za pomocą karty Visa");
    }
}
