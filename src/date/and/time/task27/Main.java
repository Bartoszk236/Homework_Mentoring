package src.date.and.time.task27;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        String date = "2024-12-16";
        LocalDate today = LocalDate.parse(date);
        System.out.println(today.getDayOfWeek());
    }
}