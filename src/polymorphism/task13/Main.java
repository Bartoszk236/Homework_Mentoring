package src.polymorphism.task13;

public class Main {
    public static void main(String[] args) {
        Printer print1 = new ImagePrinter("/desktop/images/image1");
        print1.print();
        Printer print2 = new TextPrinter("Black");
        print2.print();
    }
}