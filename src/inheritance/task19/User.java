package src.inheritance.task19;

public class User extends Person {
    private final int accountId;

    public User(String firstName, String lastName, int accountId) {
        super(firstName, lastName);
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }
}