package src.queue.task14;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(3);
        queue.add(1);
        queue.add(4);
        queue.add(2);
        queue.add(5);
        queue.remove();
        queue.remove();
        queue.forEach(System.out::println);
    }
}