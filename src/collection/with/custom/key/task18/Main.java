package src.collection.with.custom.key.task18;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Employee, String> hashMap = new HashMap<>();
        Employee employee1 = new Employee("Marek");
        Employee employee2 = new Employee("Maciej");
        Employee employee3 = new Employee("Jack");
        hashMap.put(employee1, "HR");
        hashMap.put(employee2, "IT");
        hashMap.put(employee3, "Cyber security");
        System.out.println(hashMap);
    }
}
