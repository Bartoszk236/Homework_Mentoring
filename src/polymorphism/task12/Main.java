package src.polymorphism.task12;

public class Main {
    public static void main(String[] args) {
        Game game1 = new Basketball();
        game1.play();
        Game game2 = new Football();
        game2.play();
    }
}