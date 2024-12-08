package src.practical.application.of.abstraction.task26;

public class Main {
    public static void main(String[] args) {
        Media media1 = new Audio();
        Media media2 = new Video();
        media1.play();
        media2.play();
    }
}