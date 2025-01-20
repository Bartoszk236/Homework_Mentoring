package src.function.interfaces.task5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        getRandomNumbers(5).forEach(System.out::println);
    }

    public static List<Double> getRandomNumbers(int countOfNumbers) {
        List<Double> numbers = new ArrayList<>();
        // nie przyjmuje nic, zwraca argument
        Supplier<Double> createRandomNumber = Math::random;
        for (int i = 0; i < countOfNumbers; i++) {
            numbers.add(createRandomNumber.get());
        }
        return numbers;
    }
}