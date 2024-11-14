package src.Enkapsulacja_i_modyfikatory_dostępu.task1;

public class Main {
    public static void main(String[] args) {
        Book book = new Book();
        book.setAuthor("Bartosz Kocylo");
        book.setTitle("xyz");
        book.setYearPublished(2023);

        System.out.println(book.getYearPublished());
    }
}
