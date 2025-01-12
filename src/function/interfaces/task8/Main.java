package src.function.interfaces.task8;

import java.util.function.BinaryOperator;

public class Main {
    public static void main(String[] args) {
        System.out.println(giveLargestNumber(10, 5));
    }

    public static int giveLargestNumber(int a, int b) {
        // przyjmuje dwa argumenty, zwraca to samo co przyjmuje
        BinaryOperator<Integer> max = Math::max;
        return max.apply(a, b);
    }
}