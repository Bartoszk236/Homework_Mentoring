package src.biginteger.and.bigdecimal.task7;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("854345678");
        BigInteger b = new BigInteger("76789865");
        System.out.println("Wynik: " + a.divide(b));
        System.out.println("Reszta: " + a.remainder(b));
    }
}