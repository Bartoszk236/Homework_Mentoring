package src.list.array.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList("Gosia", "Kasia", "Ania", "Bartosz"));
        // przyjmuje jeden argument, zwraca boolean
        Predicate<String> nameStartsWithA = name -> name.startsWith("A");
        for (String name : names) {
            if (nameStartsWithA.test(name)) {
                names.remove(name);
            }
        }
        names.forEach(System.out::println);
    }
}