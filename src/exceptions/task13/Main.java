package src.exceptions.task13;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        while (true) {
            try {
                insertNumbers(list);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again");
            }
        }
    }

    public static void insertNumbers(List<Integer> numbers) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!scanner.hasNextInt()) throw new NumberFormatException();
            int number = scanner.nextInt();
            numbers.add(number);
        }
    }
}