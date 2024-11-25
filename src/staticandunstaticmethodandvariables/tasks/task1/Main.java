package src.staticandunstaticmethodandvariables.tasks.task1;

public class Main {
    public static void main(String[] args) {
        //wywoałnie licznika bezpośrednio z klasy (powinno wskazywać 0)
        System.out.println(Counter.count);
        Counter counter = new Counter();
        Counter counter2 = new Counter();
        System.out.println(Counter.count);
    }
}