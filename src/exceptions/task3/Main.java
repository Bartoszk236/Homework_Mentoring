package src.exceptions.task3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Number: " + checkInputNumber());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int checkInputNumber() {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        if (a < 0) throw new IllegalArgumentException("Number must be greater than 0, insert number again");
        return a;
    }
}