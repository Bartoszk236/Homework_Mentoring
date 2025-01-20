package src.function.interfaces.task9;

import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<LocalDate, String> formatDate = date -> date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date = LocalDate.of(2003, 5, 24);
        System.out.println(date + " -> " + formatDate.apply(date));
    }
}