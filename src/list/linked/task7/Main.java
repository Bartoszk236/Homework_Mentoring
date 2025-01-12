package src.list.linked.task7;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        list.removeFirst();
        list.forEach(System.out::println);
    }
}