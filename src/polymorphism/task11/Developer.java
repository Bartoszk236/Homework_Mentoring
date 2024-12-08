package src.polymorphism.task11;

public class Developer extends Employee {
    private String name;

    public Developer(int workHours, String name) {
        super(workHours);
        this.name = name;
    }

    @Override
    void calculateSalary() {
        System.out.println("Developer salary: " + (getWorkHours() * 50) + " PLN");
    }
}