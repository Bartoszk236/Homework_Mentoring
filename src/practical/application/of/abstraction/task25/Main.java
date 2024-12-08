package src.practical.application.of.abstraction.task25;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Payment payment1 = new CashPayment(new BigDecimal("50.56"), new BigDecimal(100));
        Payment payment2 = new CardPayment(new BigDecimal(70), "123456789");

        payment1.processPayment();
        payment2.processPayment();
    }
}