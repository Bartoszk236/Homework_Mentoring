package src.function.interfaces.task7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        calculateSquare(numbers).forEach(System.out::println);
    }

    public static List<Integer> calculateSquare(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        // przyjmuje to samo co zwraca
        UnaryOperator<Integer> square = x -> x * x;
        for (Integer number : numbers) {
            result.add(square.apply(number));
        }
        return result;
    }
}