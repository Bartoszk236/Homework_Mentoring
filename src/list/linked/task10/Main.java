package src.list.linked.task10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        ArrayList<String> list = new ArrayList<>(linkedList);
        list.forEach(System.out::println);
    }
}