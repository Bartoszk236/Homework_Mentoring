package src.biginteger.and.bigdecimal.task15;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println(circleArea(new BigDecimal("3")));
    }

    public static BigDecimal circleArea(BigDecimal radius) {
        return radius.multiply(new BigDecimal("2").multiply(new BigDecimal(Math.PI)));
    }
}