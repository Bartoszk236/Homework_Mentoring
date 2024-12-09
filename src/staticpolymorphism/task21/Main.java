package src.staticpolymorphism.task21;

public class Main {
    public static void main(String[] args) {
        Shape.draw(new Circle());
        Shape.draw(new Square());
        Shape.draw(new Triangle());
    }
}