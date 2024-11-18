package src.composition.aggregation.association.task9;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Company company = new Company();
        Employee employee = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();

        employee.setSalary(new BigDecimal("1000"));
        employee2.setSalary(new BigDecimal("2000"));
        employee3.setSalary(new BigDecimal("3000"));

        company.addEmployee(employee);
        company.addEmployee(employee2);
        company.addEmployee(employee3);

        System.out.println(company.getAverageSalary());
    }
}