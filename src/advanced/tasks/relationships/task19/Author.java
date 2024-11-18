package src.advanced.tasks.relationships.task19;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private List<Book> books = new ArrayList<>();

    public Author addBook(Book book){
        books.add(book);
        return this;
    }
}