package src.date.and.time.task28;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        System.out.println(numberOfHours("16:51:36", "23:34:12"));
    }

    public static int numberOfHours(String a, String b) {
        LocalTime time1 = LocalTime.parse(a);
        LocalTime time2 = LocalTime.parse(b);
        return Math.abs(time1.getHour() - time2.getHour());
    }
}