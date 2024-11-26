package src.staticandunstaticmethodandvariables.tasks.task1;

public class Counter {
    // zmienna statyczna odnosi się do klasy
    public static int count = 0;

    public Counter() {
        increment();
    }

    //metoda statyczna która może używać zmiennych statycznych
    public static void increment(){
        count++;
    }
}