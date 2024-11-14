package src.Enkapsulacja_i_modyfikatory_dostępu.task1;

import java.time.LocalDate;
import java.util.Date;

public class Book {
    private String title;
    private String author;
    private int yearPublished;

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public Book setYearPublished(int yearPublished) {
        if (yearPublished > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Year published is greater than current year");
        }
        this.yearPublished = yearPublished;
        return this;
    }
}
