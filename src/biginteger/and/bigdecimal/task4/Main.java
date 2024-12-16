package src.biginteger.and.bigdecimal.task4;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("50");
        BigInteger result = new BigInteger("1");
        for (int i = 1; i <= a.intValue(); i++) {
            BigInteger b = new BigInteger(String.valueOf(i));
            result = result.multiply(b);
        }
        System.out.println(result);
    }
}