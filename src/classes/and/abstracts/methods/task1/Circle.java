package src.classes.and.abstracts.methods.task1;

public class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    void calculatePerimeter() {
        System.out.println(2 * Math.PI * radius);
    }
}