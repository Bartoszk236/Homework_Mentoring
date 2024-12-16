package src.biginteger.and.bigdecimal.task2;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("234567890");
        BigInteger b = new BigInteger("876543210");
        System.out.println(greatestCommonDivisor(a, b));
    }

    public static BigInteger greatestCommonDivisor(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }
}