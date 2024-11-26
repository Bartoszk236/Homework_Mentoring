package src.staticandunstaticmethodandvariables.tasks.task19;

public class Main {
    public static void main(String[] args) {
        System.out.println(Library.getTotalBooks());
        Library library = new Library("Book1");
        Library library2 = new Library("Book2");
        Library library3 = new Library("Book3");
        System.out.println(Library.getTotalBooks());
    }
}