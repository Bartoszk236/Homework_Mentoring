package src.design.patterns.builder.with.pattern;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Person person = new Person.Builder()
                .firstName("Bartosz")
                .lastName("Kocylo")
                .birthDate(LocalDate.of(2003, 5, 24))
                .build();
        System.out.println(person);
        Person person2 = new Person.Builder()
                .firstName("Jakub")
                .lastName("Kowalski")
                .birthDate(LocalDate.of(1999, 6, 13))
                .bornPlace("Warszawa")
                .gender("Male")
                .build();
        System.out.println(person2);
        Person person3 = new Person.Builder().build();
        System.out.println(person3);
    }
}
