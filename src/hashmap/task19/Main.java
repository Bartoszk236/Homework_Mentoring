package src.hashmap.task19;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(Map.of(
                "Bartosz", 21, "Kamil", 24, "Patryk", 31));
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}