package src.list.array.task3;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Gosia", "Kasia", "Ania", "Bartosz", "Michał");
        for (String name : names) {
            System.out.println(name);
        }
    }
}