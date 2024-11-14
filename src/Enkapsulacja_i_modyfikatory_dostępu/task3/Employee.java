package src.Enkapsulacja_i_modyfikatory_dostępu.task3;

import java.math.BigDecimal;

public class Employee {
    private String name;
    private String position;
    private BigDecimal salary;

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Employee setPosition(String position) {
        this.position = position;
        return this;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Employee setSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public void increaseSalary(BigDecimal percentage) {
        setSalary(getSalary().multiply(percentage.divide(new BigDecimal(100))).add(getSalary()));
    }
}
