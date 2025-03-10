package src.design.patterns.builder.without.pattern;

import java.time.LocalDate;

public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String bornPlace;

    public Person() {
    }

    public Person(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Person(String bornPlace) {
        this.bornPlace = bornPlace;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Person(String firstName, String lastName, String gender, String bornPlace) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.bornPlace = bornPlace;
    }

    @Override
    public String toString() {
        return "Person{" +
                "birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", bornPlace='" + bornPlace + '\'' +
                '}';
    }
}
