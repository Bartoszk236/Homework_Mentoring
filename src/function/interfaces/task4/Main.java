package src.function.interfaces.task4;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        // przyjmuje argument, zwraca nic, może wykonywać metody void
        Consumer<String> sayHello = name -> System.out.println("Hello, " + name + "!");
        sayHello.accept("Bartosz");
    }
}