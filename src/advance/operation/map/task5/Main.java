package src.advance.operation.map.task5;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Integer> hashMap1 = new HashMap<>();
        HashMap<Key, Integer> hashMap2 = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            hashMap1.put(i, i);
            hashMap2.put(new Key(i), i);
        }
        Instant start = Instant.now();
        int result1 = hashMap1.get(500);
        Duration duration1 = Duration.between(start, Instant.now());
        System.out.println("HashMap with good hashing: " + duration1.toNanos() + " nanoseconds");
        Instant start2 = Instant.now();
        int result2 = hashMap2.get(new Key(500));
        Duration duration2 = Duration.between(start2, Instant.now());
        System.out.println("HashMap with wrong hashing: " + duration2.toNanos() + " nanoseconds");
        // ogromna różnica w czasie wyszukania za pomocą klucza na korzyść poprawnie zrobionej metody hashCode()
    }
}
