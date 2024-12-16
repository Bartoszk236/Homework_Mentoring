package src.biginteger.and.bigdecimal.task11;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("10.50");
        BigDecimal b = new BigDecimal("5.75");
        System.out.println(a.add(b));
        System.out.println(a.subtract(b));
        System.out.println(a.multiply(b));
        System.out.println(a.divide(b, 2, RoundingMode.HALF_EVEN));
    }
}