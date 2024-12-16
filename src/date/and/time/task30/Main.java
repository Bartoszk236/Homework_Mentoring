package src.date.and.time.task30;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String format = "dd-MM-yyyy HH:mm";
        System.out.println(formatDateTime(now, format));
    }

    public static String formatDateTime(LocalDateTime dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }
}