package src.polymorphism.task11;

public abstract class Employee {
    private int workHours;

    public Employee(int workHours) {
        this.workHours = workHours;
    }

    abstract void calculateSalary();

    public int getWorkHours() {
        return workHours;
    }
}