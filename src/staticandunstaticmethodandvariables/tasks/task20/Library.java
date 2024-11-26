package src.staticandunstaticmethodandvariables.tasks.task20;

public class Library {
    private int idBook;
    private static int count = 0;

    public Library(int idBook) {
        this.idBook = idBook;
        incrementCount();
    }

    public static void incrementCount() {
        count++;
    }
    public static int getCount() {
        return count;
    }
    @Override
    public String toString() {
        return "Book id: " + idBook;
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing Library"); // komunikat w konsoli jeśli zadziała metoda finalize
    }
}