package src.hashmap.task22;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Character, Integer> map = new HashMap<>();
        String word = "hello world";
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (ch == ' ') continue;
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }
        }
        map.forEach((k, v) -> System.out.println(k + " : " + v));
    }
}