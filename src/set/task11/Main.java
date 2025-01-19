package src.set.task11;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        HashSet<Integer> hashSet1 = new HashSet<>();
        HashSet<Integer> hashSet2 = new HashSet<>();
        hashSet1.add(10);
        hashSet1.add(20);
        hashSet1.add(30);
        hashSet1.add(40);
        hashSet2.add(10);
        hashSet2.add(25);
        hashSet2.add(30);
        hashSet2.add(45);

        HashSet<Integer> union = new HashSet<>(hashSet1);
        union.addAll(hashSet2);
        // scalenie hashsetów, brak duplikatów
        System.out.println("Union: " + union);

        HashSet<Integer> intersection = new HashSet<>(hashSet1);
        intersection.retainAll(hashSet2);
        // te same elementy
        System.out.println("Intersection: " + intersection);

        HashSet<Integer> difference = new HashSet<>(hashSet1);
        difference.removeAll(hashSet2);
        // usunięcie elementów które znajdują się w dwóch hashsetach, pozostawienie tylko pozostałych z hashset1
        System.out.println("Difference: " + difference);
    }
}