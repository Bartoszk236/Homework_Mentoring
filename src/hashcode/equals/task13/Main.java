package src.hashcode.equals.task13;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person(21, "Bartosz");
        Person person2 = new Person(21, "Bartosz");

        HashSet<Person> hashSet = new HashSet<>();
        hashSet.add(person1);
        hashSet.add(person2);
        // bez dostosowania metod hashCode i equals obydwa obiekty trafiają do hashSet
        System.out.println(hashSet);
    }
}