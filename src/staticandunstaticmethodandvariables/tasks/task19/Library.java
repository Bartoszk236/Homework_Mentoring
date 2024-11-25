package src.staticandunstaticmethodandvariables.tasks.task19;

public class Library {
    private static int totalBooks = 0;
    public String bookTitle;

    public static void incrementTotalBooks() {
        totalBooks++;
    }

    public static int getTotalBooks() {
        return totalBooks;
    }

    public Library(String bookTitle) {
        this.bookTitle = bookTitle;
        incrementTotalBooks();
    }
}