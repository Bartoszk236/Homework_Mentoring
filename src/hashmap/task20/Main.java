package src.hashmap.task20;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(Map.of(
                "Bartosz", 21, "Kamil", 24, "Patryk", 31, "Jan", 80));
        if (map.containsKey("Jan")) {
            System.out.println(map.get("Jan"));
        } else {
            System.out.println("List not found record with key Jan");
        }
    }
}