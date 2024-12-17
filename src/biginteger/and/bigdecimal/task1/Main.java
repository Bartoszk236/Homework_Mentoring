package src.biginteger.and.bigdecimal.task1;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("34567898765434678");
        BigInteger b = new BigInteger("9876545678908765");
        BigInteger sum = a.add(b);
        BigInteger product = a.multiply(b);
        System.out.println("Sum: " + sum);
        System.out.println("Product: " + product);
    }
}