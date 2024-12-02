package src.inheritance.task19;

public class Admin extends Person{
    private final int accountId;

    public Admin(String firstName, String lastName, int accountId) {
        super(firstName, lastName);
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }
}