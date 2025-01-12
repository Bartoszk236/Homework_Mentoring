package src.function.interfaces.task10;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Gosia", "Kasia", "Ania");
        printAllNames(names);
    }

    public static void printAllNames(List<String> names) {
        // przymuje argument, zwraca nic, może wykonywać tylko metody void
        Consumer<String> printFirstName = name -> System.out.println("Name: " + name);
        names.forEach(printFirstName);
    }
}