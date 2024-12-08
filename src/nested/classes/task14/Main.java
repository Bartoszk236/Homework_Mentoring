package src.nested.classes.task14;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(1, "Przepisy Basi", "Barbara Kowalska");
        library.addBook(2, "Przepisy Kasi", "Katarzyna Malinowska");
        library.displayAllBooks();
        library.removeBook(2);
        library.displayAllBooks();
    }
}