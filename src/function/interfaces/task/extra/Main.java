package src.function.interfaces.task.extra;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Gosia", "Kasia", "Ania", "Baltazar");
        printLengthOfNamesThatHaveMoreThanFourLetters(names);
    }

    public static void printLengthOfNamesThatHaveMoreThanFourLetters(List<String> names) {
        // przyjmuje argument, zwraca boolean
        Predicate<String> nameHaveMoreThanFourLetters = name -> name.length() > 4;
        // przyjmuje argument, zwraca argument
        Function<String, Integer> getLengthOfNames = String::length;
        // przyjmuje argument, zwraca nic, może wykonywać tylko metody void
        Consumer<Integer> printLength = System.out::println;
        names.stream().
                filter(nameHaveMoreThanFourLetters)
                .map(getLengthOfNames)
                .forEach(printLength);
    }
}