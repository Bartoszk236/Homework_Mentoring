package src.exceptions.task14;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> reservationsDataBase = Arrays.asList("ABC123", "ABC456", "ABC789");
        try {
            checkServerStatus(true);
            if (checkReservationNumber(reservationsDataBase, scanner.nextLine())) System.out.println("Reservation number is valid");
        } catch (ServerOverloadException e) {
            System.out.println(e.getMessage());
        } catch (InvalidReservationException e) {
            System.out.println(e.getMessage());
            System.out.println("Please enter a valid reservation number");
            System.out.println("If you have any problem please contact with us");
        }
    }

    public static boolean checkReservationNumber(List<String> reservationsDataBase, String reservationNumber) {
        if (reservationsDataBase.stream().anyMatch(reservationNumber::equals)) {
            return true;
        } else {
            throw new InvalidReservationException("Invalid reservation number: " + reservationNumber);
        }
    }

    public static void checkServerStatus(boolean serverStatus) {
        if (!serverStatus) {
            throw new ServerOverloadException();
        }
    }
}