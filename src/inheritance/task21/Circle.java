package src.inheritance.task21;

public class Circle extends Shape {
    private double radius;

    public Circle(String nameOfShape, double radius) {
        super(nameOfShape);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public Circle setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public void area() {
        super.area();
        System.out.println(Math.PI * Math.pow(radius, 2));
    }
}