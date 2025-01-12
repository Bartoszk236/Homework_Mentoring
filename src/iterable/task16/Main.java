package src.iterable.task16;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int element = iterator.next();
            if (element % 2 == 0) System.out.println(element);
        }
    }
}