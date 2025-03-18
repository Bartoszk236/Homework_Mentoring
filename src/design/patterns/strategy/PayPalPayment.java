package src.design.patterns.strategy;

import java.math.BigDecimal;

public class PayPalPayment implements PaymentStrategy {

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("Zapłacono " + amount + " za pomocą PayPal");
    }
}
