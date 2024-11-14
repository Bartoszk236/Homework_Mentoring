package src.Kompozycja_Agregacja_Asocjacja.task7;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Book book = new Book();
        book.setAuthor("Bartosz");
        Book book2 = new Book();
        book2.setAuthor("Bob");
        Book book3 = new Book();
        book3.setAuthor("Bartosz");

        library.addBook(book);
        library.addBook(book2);
        library.addBook(book3);

        List<Book> books = library.getBooksByAuthor("Bartosz");

        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i).getAuthor());
        }
    }
}
