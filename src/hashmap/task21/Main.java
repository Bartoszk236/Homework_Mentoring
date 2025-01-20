package src.hashmap.task21;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(Map.of(
                "Bartosz", 21, "Kamil", 24, "Patryk", 31, "Jan", 80));
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}