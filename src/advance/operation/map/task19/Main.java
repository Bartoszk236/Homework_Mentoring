package src.advance.operation.map.task19;

import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            treeMap.put("Number_"+ i, i);
        }
        System.out.println("Higher: " + treeMap.higherEntry("Number_5"));
        System.out.println("Lower: " + treeMap.lowerEntry("Number_5"));
    }
}
