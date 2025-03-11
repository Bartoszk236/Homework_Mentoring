package src.design.patterns.builder.without.pattern;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Bartosz", "Kocylo", "Male", "Warszawa");
        System.out.println(person);
    }
}
