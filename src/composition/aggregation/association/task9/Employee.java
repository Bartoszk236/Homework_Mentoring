package src.composition.aggregation.association.task9;

import java.math.BigDecimal;

public class Employee {
    private BigDecimal salary;

    public BigDecimal getSalary() {
        return salary;
    }

    public Employee setSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }
}