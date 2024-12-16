package src.biginteger.and.bigdecimal.task5;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger result = new BigInteger("0");
        for (int i = 1; i <= 1000000000; i++) {
            result = result.add(new BigInteger(String.valueOf(i)));
        }
        System.out.println(result);
    }
}