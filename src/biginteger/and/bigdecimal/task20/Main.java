package src.biginteger.and.bigdecimal.task20;

import java.math.BigDecimal;
import java.math.MathContext;

public class Main {
    public static void main(String[] args) {
        String number = "15.5";
        System.out.println(getRoot(number));
    }

    public static BigDecimal getRoot(String number) {
        return new BigDecimal(number).sqrt(MathContext.DECIMAL32);
    }
}