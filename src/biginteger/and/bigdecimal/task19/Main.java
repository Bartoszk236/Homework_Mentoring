package src.biginteger.and.bigdecimal.task19;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("2");
        BigDecimal b = new BigDecimal("3");
        System.out.println(sumOfSquares(a, b));
    }

    public static BigDecimal sumOfSquares(BigDecimal a, BigDecimal b) {
        return a.pow(2).add(b.pow(2));
    }
}