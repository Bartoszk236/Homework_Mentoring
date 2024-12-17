package src.biginteger.and.bigdecimal.task18;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        BigDecimal netPrice = new BigDecimal("3.99");
        System.out.println(calculateGrossPrice(netPrice));
    }

    public static BigDecimal calculateGrossPrice(BigDecimal netPrice) {
        return netPrice.add(netPrice.multiply(new BigDecimal("0.23"))).setScale(2, RoundingMode.CEILING);
    }
}