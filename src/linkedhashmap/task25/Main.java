package src.linkedhashmap.task25;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>(Map.of(
                0, "zero", 1, "one", 2,
                "two", 3, "three", 4, "four", 5, "five"
        ));
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>(map);
        for (int i = 0; i < 6; i++) {
            System.out.println("Hashmap: " + map.get(i));
            System.out.println("LinkedHashMap: " + linkedHashMap.get(i));

        }
    }
}
