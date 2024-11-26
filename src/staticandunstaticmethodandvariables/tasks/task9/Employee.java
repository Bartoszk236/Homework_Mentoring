package src.staticandunstaticmethodandvariables.tasks.task9;

public class Employee {
    public static String companyName;
    public String employeeName;

    @Override
    public String toString() {
        return "Company name: " + companyName + "\nEmployee name: " + employeeName;
    }
}
