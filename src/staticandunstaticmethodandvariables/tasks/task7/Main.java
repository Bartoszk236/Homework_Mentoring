package src.staticandunstaticmethodandvariables.tasks.task7;

public class Main {
    public static void main(String[] args) {
        BankAccount.setBankName("Santander");
        BankAccount account1 = new BankAccount(500);
        BankAccount account2 = new BankAccount(600);
        BankAccount account3 = new BankAccount(700);
        System.out.println(account1);
        System.out.println(account2);
        System.out.println(account3);
    }
}