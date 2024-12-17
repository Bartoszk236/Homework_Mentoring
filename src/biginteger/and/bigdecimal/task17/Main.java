package src.biginteger.and.bigdecimal.task17;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("5.98");
        BigDecimal b = new BigDecimal("3.01");
        System.out.println(a.divide(b, 5, RoundingMode.HALF_UP));
    }
}