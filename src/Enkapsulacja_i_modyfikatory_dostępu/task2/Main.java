package src.Enkapsulacja_i_modyfikatory_dostępu.task2;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(new BigInteger("1234567890123456789012345678"), new BigDecimal(0));
        bankAccount.deposit(new BigDecimal(200));
        bankAccount.withdraw(new BigDecimal(301));

        System.out.println(bankAccount.getBalance());
    }
}
