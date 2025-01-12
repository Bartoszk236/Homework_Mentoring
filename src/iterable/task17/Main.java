package src.iterable.task17;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Bartosz", "Kamil", "Michał"));
        List<String> names2 = new ArrayList<>();
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            names2.add(iterator.next());
        }
        names2.forEach(System.out::println);
    }
}