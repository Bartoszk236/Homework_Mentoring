package src.polymorphism.task11;

public class Manager extends Employee {
    private String department;

    public Manager(int workHours, String department) {
        super(workHours);
        this.department = department;
    }

    @Override
    void calculateSalary() {
        System.out.println("Manager salary: " + (getWorkHours() * 45) + " PLN");
    }
}