package src.function.interfaces.task6;

import java.util.function.BiPredicate;

public class Main {
    public static void main(String[] args) {
        // przyjmuje dwa argumenty, zwraca boolean
        BiPredicate<String, String> theSame = String::equals;
        System.out.println(theSame.test("Bartosz", "Bartosz"));
    }
}