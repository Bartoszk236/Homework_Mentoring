package src.practical.application.of.abstraction.task25;

import java.math.BigDecimal;

public class CardPayment extends Payment {
    private String cardNumber;

    public CardPayment(BigDecimal amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    void processPayment() {
        System.out.println("Payment successful");
        System.out.println("The bill:");
        System.out.println(getAmount() + "$ was debited from the card " + cardNumber);
    }
}
