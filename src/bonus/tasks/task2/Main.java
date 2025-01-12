package src.bonus.tasks.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names1 = new ArrayList<>(Arrays.asList("Bartosz", "Kamil", "Gosia", "Kasia", "Ania"));
        List<String> names2 = new ArrayList<>(Arrays.asList("Bartosz", "Ania", "Beata"));
        System.out.println("The same: ");
        getTheSame(names1, names2).forEach(System.out::println);
        System.out.println("The difference: ");
        getTheDifference(names1, names2).forEach(System.out::println);
    }

    public static List<String> getTheSame(List<String> names1, List<String> names2) {
        return names1.stream().filter(name -> names2.contains(name)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<String> getTheDifference(List<String> names1, List<String> names2) {
        List<String> result = new ArrayList<>();
        names1.stream().filter(n -> !names2.contains(n)).forEach(result::add);
        names2.stream().filter(n -> !names1.contains(n)).forEach(result::add);
        return result;
    }
}
