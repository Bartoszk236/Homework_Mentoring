package src.date.and.time.task23;


import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(2024, 12, 1);
        Period period = Period.between(date, now);
        System.out.println(period.getDays());
    }
}