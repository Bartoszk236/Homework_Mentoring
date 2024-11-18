package src.advanced.tasks.relationships.task19;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private List<Author> authors = new ArrayList<>();

    public Book addAuthor(Author author) {
        authors.add(author);
        return this;
    }
}