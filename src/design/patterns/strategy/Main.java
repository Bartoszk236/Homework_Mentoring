package src.design.patterns.strategy;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Payment payment = new Payment();

        payment.setStrategy(new PayPalPayment());
        payment.pay(new BigDecimal("100.00"));

        payment.setStrategy(new MasterCardPayment());
        payment.pay(new BigDecimal("100.00"));

        payment.setStrategy(new VisaPayment());
        payment.pay(new BigDecimal("100.00"));
    }
}
