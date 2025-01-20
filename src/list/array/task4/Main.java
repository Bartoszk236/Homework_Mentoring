package src.list.array.task4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Gosia", "Kasia", "Ania", "Bartosz", "Michał");
        sortAlphabetically(names).forEach(System.out::println);
    }

    public static List<String> sortAlphabetically(List<String> list) {
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            for (int j = i + 1; j < listSize; j++) {
                String temp = list.get(i);
                String temp2 = list.get(j);
                if (temp.compareTo(temp2) > 0) {
                    list.set(i, temp2);
                    list.set(j, temp);
                }
            }
        }
        return list;
    }
}