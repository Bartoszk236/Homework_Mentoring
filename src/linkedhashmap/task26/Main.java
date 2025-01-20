package src.linkedhashmap.task26;

import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("Monday", 1);
        map.put("Tuesday", 2);
        map.put("Wednesday", 3);
        map.put("Thursday", 4);
        map.put("Friday", 5);
        map.put("Saturday", 6);
        map.put("Sunday", 7);
        map.remove("Monday");
        map.put("Monday", 0);
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}