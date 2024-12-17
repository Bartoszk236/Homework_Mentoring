package src.date.and.time.task26;

import java.time.*;

public class Main {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate lastBirthday = LocalDate.of(2024, 5, 24);
        LocalDateTime lastBirthdayLocalDateTime = lastBirthday.atStartOfDay();
        Duration duration = Duration.between(lastBirthdayLocalDateTime, localDateTime);
        System.out.println("Dni: " + duration.toDays());
        System.out.println("Godziny: " + duration.toHoursPart());
        System.out.println("Minuty: " + duration.toMinutesPart());

    }
}