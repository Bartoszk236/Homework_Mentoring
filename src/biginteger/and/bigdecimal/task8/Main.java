package src.biginteger.and.bigdecimal.task8;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("1");
        BigInteger b = new BigInteger("1");
        System.out.println(a.and(b));
        System.out.println(a.or(b));
        System.out.println(a.xor(b));
    }
}