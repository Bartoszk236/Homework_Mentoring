package src.hashcode.equals.task12;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person(21, "Bartosz");
        Person person2 = new Person(21, "Bartosz");

        HashSet<Person> hashSet = new HashSet<>();
        hashSet.add(person1); hashSet.add(person2);
        // hashcode był ten sam, metoda equals zwróciła true przez co person2 nie został dodany do hashset
        System.out.println(hashSet);
    }
}