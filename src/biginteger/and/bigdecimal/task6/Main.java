package src.biginteger.and.bigdecimal.task6;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("34567890987654334567");
        System.out.println(isPrime(a));
    }

    public static boolean isPrime(BigInteger n) {
        for (int i = 2; i < n.intValue(); i++) {
           if (n.remainder(new BigInteger(String.valueOf(i))).equals(new BigInteger("0"))) {
               return false;
           }
        }
        return true;
    }
}