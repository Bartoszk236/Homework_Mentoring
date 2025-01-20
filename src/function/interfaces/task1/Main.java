package src.function.interfaces.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("Cat", "Dog", "Bird", "Chicken");
        calculateLengthOfWords(words).forEach(System.out::println);
    }

    public static List<Integer> calculateLengthOfWords(List<String> words) {
        List<Integer> lengthOfWords = new ArrayList<>();
        // przyjmuje jeden argument, zwraca jeden agument
        Function<String, Integer> getLengthOfWord = String::length;
        words.forEach(word -> lengthOfWords.add(getLengthOfWord.apply(word)));
        return lengthOfWords;
    }
}