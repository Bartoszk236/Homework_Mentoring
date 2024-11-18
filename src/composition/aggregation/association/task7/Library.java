package src.composition.aggregation.association.task7;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();

    public Library addBook(Book book) {
        books.add(book);
        return this;
    }

    public List<Book> getBooksByAuthor(String author) {
        return books.stream().filter(book -> book.getAuthor().equals(author)).toList();
    }
}