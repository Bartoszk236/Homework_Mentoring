package src.date.and.time.task29;

import java.time.LocalTime;
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
        LocalTime warsaw = LocalTime.now(ZoneId.of("Europe/Warsaw"));
        LocalTime newYork = LocalTime.now(ZoneId.of("America/New_York"));
        LocalTime tokyo = LocalTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.println("Warsaw: " + warsaw);
        System.out.println("New York: " + newYork);
        System.out.println("Tokyo: " + tokyo);
    }
}