package src.biginteger.and.bigdecimal.task3;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger base = new BigInteger("45678909876543");
        int exponent = 2;
        BigInteger result = base.pow(exponent);
        System.out.println(result);
    }
}