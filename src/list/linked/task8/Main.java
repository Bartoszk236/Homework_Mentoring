package src.list.linked.task8;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
