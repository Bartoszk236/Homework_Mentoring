package src.function.interfaces.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        deleteOddNumbers(numbers).forEach(System.out::println);
    }

    public static List<Integer> deleteOddNumbers(List<Integer> numbers) {
        List<Integer> onlyEvenNumbers = new ArrayList<>();
        // przymuje jeden argument, zwraca boolean
        Predicate<Integer> isEven = number -> number % 2 == 0;
        numbers.stream().filter(isEven).forEach(onlyEvenNumbers::add);
        return onlyEvenNumbers;
    }
}