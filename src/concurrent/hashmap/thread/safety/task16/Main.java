package src.concurrent.hashmap.thread.safety.task16;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();
        Supplier<Integer> randomNumberGenerator = () -> (int) (Math.random() * 100);
        Predicate<Integer> isPrime = number -> number % 2 == 0;

        Runnable adding = () -> {
            for (int i = 1; i <= 10; i++) {
                hashMap.put("number " + i, randomNumberGenerator.get());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable editing = () -> {
            for (int i = 1; i <= 10; i++) {
                String key = "number " + i;
                if (hashMap.containsKey(key)) {
                    Integer value = hashMap.get(key);
                    if (!isPrime.test(value)) {
                        hashMap.remove(key);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread addingTask = new Thread(adding);
        Thread editingTask = new Thread(editing);

        addingTask.start();
        editingTask.start();

        try {
            addingTask.join();
            editingTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(hashMap);
    }
}