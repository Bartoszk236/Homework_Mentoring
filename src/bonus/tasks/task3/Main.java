package src.bonus.tasks.task3;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        LinkedList<Integer> linkedList = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Instant start = Instant.now();
        operationOnArrayList(arrayList);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Operation on ArrayList: " + duration.toNanos() + " nanoseconds");
        Instant start2 = Instant.now();
        operationOnLinkedList(linkedList);
        Instant end2 = Instant.now();
        Duration duration2 = Duration.between(start2, end2);
        System.out.println("Operation on LinkedList: " + duration2.toNanos() + " nanoseconds");
    }

    public static ArrayList<Integer> operationOnArrayList(ArrayList<Integer> arrayList) {
        arrayList.remove(arrayList.size() / 2);
        arrayList.add(arrayList.size() / 2, 50);
        arrayList.remove(0);
        arrayList.add(0, 0);
        arrayList.remove(arrayList.size() - 1);
        arrayList.add(arrayList.size() - 1, 100);

        return arrayList;
    }

    public static LinkedList<Integer> operationOnLinkedList(LinkedList<Integer> linkedList) {
        linkedList.remove(linkedList.size() / 2);
        linkedList.add(linkedList.size() / 2, 50);
        linkedList.remove(0);
        linkedList.add(0, 0);
        linkedList.remove(linkedList.size() - 1);
        linkedList.add(linkedList.size() - 1, 100);
        return linkedList;
    }
}