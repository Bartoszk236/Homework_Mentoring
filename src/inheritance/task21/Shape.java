package src.inheritance.task21;

public class Shape {
    private final String nameOfShape;

    public Shape(String nameOfShape) {
        this.nameOfShape = nameOfShape;
    }

    public String getNameOfShape() {
        return nameOfShape;
    }

    public void area() {
        System.out.println("Area of " + nameOfShape + ": ");
    }
}