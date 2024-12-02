package src.inheritance.task21;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String nameOfShape, double height, double width) {
        super(nameOfShape);
        this.height = height;
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle setHeight(double height) {
        this.height = height;
        return this;
    }

    public double getWidth() {
        return width;
    }

    public Rectangle setWidth(double width) {
        this.width = width;
        return this;
    }

    @Override
    public void area() {
        super.area();
        System.out.println(width * height);
    }
}