package src.advance.operation.map.task20;

import java.util.HashSet;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Predicate<String> wordStartWithA = s -> s.startsWith("a") || s.startsWith("A");
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("Gosia"); hashSet.add("Kasia"); hashSet.add("Ania"); hashSet.add("Bartosz");
        HashSet<String> newHashSet = new HashSet<>();
        for (String word : hashSet) {
            if (!wordStartWithA.test(word)) {
                newHashSet.add(word);
            }
        }
        System.out.println(newHashSet);
    }
}
