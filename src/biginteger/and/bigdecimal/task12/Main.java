package src.biginteger.and.bigdecimal.task12;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("1.3333333333333333");
        System.out.println(round(a, 2));
    }

    public static BigDecimal round(BigDecimal a, int scale) {
        return a.setScale(scale, RoundingMode.HALF_UP);
    }
}