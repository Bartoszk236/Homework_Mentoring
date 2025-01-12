package src.iterable.task18;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(-1, 2, -3, 4, -5, 6, -7, 8, -9));
        ListIterator<Integer> iterator = list.listIterator();
        while (iterator.hasNext()) {
            int element = iterator.next();
            if (element < 0) {
                iterator.set(element * -1);
            }
        }
        list.forEach(System.out::println);
    }
}