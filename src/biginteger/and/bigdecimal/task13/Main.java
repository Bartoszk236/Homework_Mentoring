package src.biginteger.and.bigdecimal.task13;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal ratePercent = new BigDecimal("5");
        System.out.println(compoundInterest(amount, ratePercent, 10).setScale(
                2, RoundingMode.HALF_EVEN));
    }

    public static BigDecimal compoundInterest(BigDecimal amount, BigDecimal rate, int years) {
        return amount.multiply((BigDecimal.ONE.add(rate.divide(
                new BigDecimal(100), 2, RoundingMode.HALF_UP))).pow(years));
    }
}