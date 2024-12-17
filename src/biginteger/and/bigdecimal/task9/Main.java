package src.biginteger.and.bigdecimal.task9;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger population1 = new BigInteger("6375000000");
        BigInteger population2 = new BigInteger("8025000000");
        System.out.println((population1.subtract(population2).abs()));
    }
}