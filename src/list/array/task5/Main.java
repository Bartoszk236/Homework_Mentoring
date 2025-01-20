package src.list.array.task5;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Gosia", "Kasia", "Anna", "Bartosz", "Michał");
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals("Anna")) {
                System.out.println(i);
            }
        }
    }
}