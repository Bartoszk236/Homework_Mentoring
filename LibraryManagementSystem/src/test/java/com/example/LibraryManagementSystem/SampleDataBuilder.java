package com.example.LibraryManagementSystem;

import com.example.LibraryManagementSystem.entity.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SampleDataBuilder {
    @Autowired
    private TestEntityManager em;
    private final Faker faker = new Faker();

    /*
    10x Authors
    10x Categories
    10x Students
    20x Books with random Author and Category
    every Book have from 0 to 15 BorrowRecords returned (with not null returnedAt)
    and random chance to have 1x BorrowRecord with null returnedAt
    BorrowRecord have random Student
     */
    public void initSampleData() {
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Author author = createAuthor();
            authors.add(author);
            em.persist(author);
        }

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category category = createCategory();
            categories.add(category);
            em.persist(category);
        }

        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = createStudent();
            students.add(student);
            em.persist(student);
        }

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Book book = createBook(authors.get(rollIndexMaxNine()), categories.get(rollIndexMaxNine()));
            books.add(book);
            em.persist(book);
        }

        int randomNumberOfReturnedBooks = faker.number().numberBetween(0, 15);
        for (Book book : books) {
            for (int i = 0; i < randomNumberOfReturnedBooks; i++) {
                em.persist(createBorrowRecord(book, true, students.get(rollIndexMaxNine())));
            }
            if (faker.bool().bool()) {
                em.persist(createBorrowRecord(book, false,  students.get(rollIndexMaxNine())));
            }
        }
        em.flush();
        em.clear();
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setName(faker.name().fullName());
        return author;
    }

    private Book createBook(Author author, Category category) {
        Book book = new Book();
        book.setTitle(faker.book().title());
        book.setIsbn(faker.regexify("[0-9]{13}"));
        book.addAuthor(author);
        book.setPagesNumber(faker.number().numberBetween(100, 1000));
        book.setDigitalCopyAvailable(faker.bool().bool());
        book.setCategory(category);
        book.setReleaseDate(LocalDate.now().minusYears(faker.number().numberBetween(1,10)));
        return book;
    }

    private BorrowRecord createBorrowRecord(Book book, boolean returnedBook, Student student) {
        int random1 = faker.number().numberBetween(1, 100);

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBorrowDate(LocalDate.now().minusDays(random1));
        if (returnedBook) {
            int random2 = faker.number().numberBetween(1, random1);
            borrowRecord.setReturnDate(LocalDate.now().minusDays(random2));
        }
        borrowRecord.setBook(book);
        borrowRecord.setStudent(student);
        return borrowRecord;
    }

    private Category createCategory() {
        Category category = new Category();
        category.setName(faker.regexify("[a-zA-z]{10}"));
        return category;
    }

    private Student createStudent() {
        Student student = new Student();
        student.setName(faker.name().fullName());
        return student;
    }

    private int rollIndexMaxNine() {
        return faker.number().numberBetween(1, 9);
    }
}
