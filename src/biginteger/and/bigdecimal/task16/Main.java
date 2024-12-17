package src.biginteger.and.bigdecimal.task16;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        double a = 1.00;
        double b = 0.99;
        System.out.println(a - b);
        System.out.println(subtract(a, b));
    }

    public static BigDecimal subtract(double a, double b) {
        BigDecimal c = new BigDecimal(String.valueOf(a));
        BigDecimal d = new BigDecimal(String.valueOf(b));
        return c.subtract(d);
    }
}