package src.classes.and.abstracts.methods.task1;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, double height, double width) {
        super(color);
        this.height = height;
        this.width = width;
    }

    @Override
    void calculatePerimeter() {
        System.out.println((2 * width) + (2 * height));
    }
}