package src.staticandunstaticmethodandvariables.tasks.task5;

public class Main {
    public static void main(String[] args) {
        Example.method();
        //można wywołać metodę bez utworzenia obiektu bez pośrednio z klasy.
//        Example.method2(); //tutaj nie zadziała
        Example example = new Example(); //dopiero po utworzeniu obiektu mogę wywołać metodę nie statyczną
        example.method2();// <----
    }
}