package src.staticandunstaticmethodandvariables.tasks.task1;

public class Counter {
    // zmienna statyczna odnosi się do klasy
    public static int count = 0;

    //metoda statyczna która może używać zmiennych statycznych
    public static void increment(){
        count++;
    }

    public Counter() {
        increment();
    }
}