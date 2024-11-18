package src.encapsulation.access.modifiers.task3;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Employee employee = new Employee();
        employee.setName("Mike");
        employee.setPosition("HR");
        employee.setSalary(new BigDecimal(3000));

        employee.increaseSalary(new BigDecimal(15));

        System.out.println(employee.getSalary());
    }
}