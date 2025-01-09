package src.exceptions.task9;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        try {
            divide(list, 0).forEach(System.out::println);
        } catch (ArithmeticException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Integer> divide(List<Integer> list, int dividerIndex) throws IndexOutOfBoundsException {
        int divider = list.get(dividerIndex);
        if (divider == 0) throw new ArithmeticException("you can't divide by zero");
        return list.stream().map(x -> x / divider).toList();
    }
}