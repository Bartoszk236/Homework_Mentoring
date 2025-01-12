package src.list.array.task1;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Gosia", "Kasia", "Ania", "Bartosz", "Michał");
        // trzecie imie z lity ma indeks 2, ponieważ listy mają ineksy od 0
        System.out.println(names.get(2));
    }
}