package src.exceptions.task4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String input = scanner.nextLine();
            checkPhoneNumber(input);
            System.out.println("Phone number: " + input);
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 9) throw new InvalidFormatException("Invalid length of phone number");
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!Character.isDigit(c)) throw new InvalidFormatException("Phone number can have only digits");
        }
    }
}