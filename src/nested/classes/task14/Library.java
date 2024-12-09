package src.nested.classes.task14;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> library = new ArrayList<>();

    public void addBook(int id, String title, String author) {
        library.add(new Book(id, title, author));
        System.out.println("Added Book: " + id + " Title: " + title + " Author: " + author);
    }

    public void removeBook(int id) {
        library.removeIf(book -> book.id == id);
        System.out.println("Removed Book: " + id);
    }

    public void displayAllBooks() {
        library.forEach(book -> book.display());
    }

    private class Book {
        int id;
        String title;
        String author;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        void display(){
            System.out.println("Id: " + id + " Title: " + title + " Author: " + author);
        }
    }
}