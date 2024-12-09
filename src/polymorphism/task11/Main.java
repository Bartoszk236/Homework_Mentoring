package src.polymorphism.task11;

public class Main {
    public static void main(String[] args) {
        Employee employee1 = new Developer(160, "Bartosz");
        Employee employee2 = new Manager(160, "Security");

        employee1.calculateSalary();
        employee2.calculateSalary();
    }
}