package src.list.linked.task6;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> animalsNames = new LinkedList<>(Arrays.asList("Dog", "Cat", "Cow", "Lion"));
        animalsNames.addFirst("Giraffe");
        animalsNames.add((animalsNames.size() / 2) + 1, "Fish");
        animalsNames.addLast("Bird");
        animalsNames.forEach(System.out::println);
    }
}