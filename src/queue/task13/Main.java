package src.queue.task13;

import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        PriorityQueue<Person> queue = new PriorityQueue<>();
        Person person1 = new Person("Bartosz", 21);
        Person person2 = new Person("Jakub", 23);
        Person person3 = new Person("Kamil", 24);
        Person person4 = new Person("Michał", 24);
        queue.add(person1);
        queue.add(person2);
        queue.add(person3);
        queue.add(person4);
        System.out.println(queue.poll());
    }
}