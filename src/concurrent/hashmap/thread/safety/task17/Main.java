package src.concurrent.hashmap.thread.safety.task17;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int OPERATIONS_PER_THREAD = 10000;
    public static final int COUNT_OF_THREADS = 10;
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
//        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        Runnable task = () -> {
           for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
               map.put(i, i);
           }
        };
        Instant start = Instant.now();
        Thread[] threads = new Thread[COUNT_OF_THREADS];
        for (int i = 0; i < COUNT_OF_THREADS; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Duration duration = Duration.between(start, Instant.now());
        System.out.println("Time operation: " + duration.toMillis());
        System.out.println("It should be " + OPERATIONS_PER_THREAD + " records in map");
        System.out.println("Result: " + map.size());
        /**
         * wydajność czsowa jest bardzo podobna, lecz zwykła mapa nie jest przystosowana do
         * jednoczesnej edycji w wielu wątkach. Pojawiają się błędy, w hashMap powinno
         * się znaleść 10000 elementów, lecz po teście można zauważyć że jest ich więcej,
         * co w przypadku ConcurrentHashMap nie występuje
         */
    }
}
