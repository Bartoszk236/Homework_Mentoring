package src.advance.operation.map.task4;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Key, Integer> hashMap = new HashMap<>();
        hashMap.put(new Key(10), 10);
        hashMap.put(new Key(20), 20);
        hashMap.put(new Key(30), 30);
        hashMap.put(new Key(40), 40);
        System.out.println(hashMap);
        // dzięki metodzie equals mimo tego samego hashu wartości mogą być w hashmapie
    }
}
