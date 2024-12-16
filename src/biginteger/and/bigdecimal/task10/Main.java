package src.biginteger.and.bigdecimal.task10;

import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BigInteger a = BigInteger.probablePrime(100, new Random());
        System.out.println(a);
    }
}