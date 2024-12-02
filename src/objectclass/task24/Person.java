package src.objectclass.task24;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        return this.firstName.equals(((Person) obj).firstName) && this.lastName.equals(((Person) obj).lastName);
    }
}