package src.classes.and.abstracts.methods.task1;

public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle("Red", 5);
        Shape rectangle = new Rectangle("Blue", 5, 5);

        circle.calculatePerimeter();
        rectangle.calculatePerimeter();
    }
}