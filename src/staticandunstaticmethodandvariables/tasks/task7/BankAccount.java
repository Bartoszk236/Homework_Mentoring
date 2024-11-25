package src.staticandunstaticmethodandvariables.tasks.task7;

public class BankAccount {
    private static String bankName;
    private int accountNumber;

    public static void setBankName(String bankName) {
        BankAccount.bankName = bankName;
    }

    public BankAccount(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Name: " + bankName + "\nAccount number: " + accountNumber;
    }
}