package src.design.patterns.builder.with.pattern;

import java.time.LocalDate;

public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String bornPlace;

    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.gender = builder.gender;
        this.bornPlace = builder.bornPlace;
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

    public static class Builder {
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String gender;
        private String bornPlace;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder bornPlace(String bornPlace) {
            this.bornPlace = bornPlace;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
