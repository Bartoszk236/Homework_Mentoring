package src.Kompozycja_Agregacja_Asocjacja.task9;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private List<Employee> employees = new ArrayList<>();

    public Company addEmployee(Employee employee) {
        employees.add(employee);
        return this;
    }

    public BigDecimal getAverageSalary() {
        if (employees.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (Employee employee : employees) {
            total = total.add(employee.getSalary());
        }
         return total.divide(BigDecimal.valueOf(employees.size()));
    }
}
