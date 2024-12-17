package src.biginteger.and.bigdecimal.task14;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal AAPL = new BigDecimal("250.25");
        BigDecimal AMZN = new BigDecimal("231.06");
        System.out.println(priceDifference(AMZN, AAPL));
    }

    public static BigDecimal priceDifference(BigDecimal price1, BigDecimal price2) {
        return price1.subtract(price2).abs();
    }
}