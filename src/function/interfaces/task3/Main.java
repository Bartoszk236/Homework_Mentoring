package src.function.interfaces.task3;

import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        // przyjmuje dwa argumenty, zwraca jeden
        BiFunction<Integer, Integer, Integer> getSum = Integer::sum;
        System.out.println(getSum.apply(5, 7));
    }
}
