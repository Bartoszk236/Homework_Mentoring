package src.set.task10;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        TreeSet<Integer> treeSet = new TreeSet<>();

        Instant instant = Instant.now();
        hashSet.addAll(Arrays.asList(10, 25, 8, 10));
        Duration duration = Duration.between(instant, Instant.now());

        Instant instant2 = Instant.now();
        linkedHashSet.addAll(Arrays.asList(11, 26, 9, 11));
        Duration duration2 = Duration.between(instant2, Instant.now());

        Instant instant3 = Instant.now();
        treeSet.addAll(Arrays.asList(12, 27, 10, 12));
        Duration duration3 = Duration.between(instant3, Instant.now());

        // dowolna kolejność dodawania, nie dodanie duplikatu "10"
        System.out.println("HashSet: " + hashSet + " time operation: " + duration.toNanos());
        // zachowana kolejność dodawania, nie dodanie duplikatu "11"
        System.out.println("LinkedHashSet: " + linkedHashSet +" time operation: " + duration2.toNanos());
        // uporządkowana kolejność domyślnie rosnąco, nie dodanie duplikatu "12"
        System.out.println("TreeSet: " + treeSet +" time operation: " + duration3.toNanos());
        // opercaje dodawania, miej więcej trwają tyle samo dla tak małej ilości danych
    }
}