package src.staticandunstaticmethodandvariables.tasks.task12;

public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        System.out.println(Implementation.getMax(numbers)); // możemy wywołać statyczną metodę bezpośrednio z klasy, bez konieczności inicjalizaji obiektu klasy
        Implementation implementation = new Implementation();
        implementation.displayArray(numbers); // dopiero po utworzniu obiektui danej klasy możemy mieć dostęp do jego metod
    }
}