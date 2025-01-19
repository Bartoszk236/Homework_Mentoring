package src.iterator.task14;

import java.util.HashSet;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(30);
        hashSet.add(40);
        Iterator<Integer> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() > 10) iterator.remove();
        }
        System.out.println(hashSet);
    }
}