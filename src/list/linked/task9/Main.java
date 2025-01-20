package src.list.linked.task9;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        reverseList(list).forEach(System.out::println);
    }

    public static LinkedList<String> reverseList(LinkedList<String> list) {
        LinkedList<String> result = new LinkedList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}