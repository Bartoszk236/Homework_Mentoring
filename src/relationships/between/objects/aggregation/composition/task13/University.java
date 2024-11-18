package src.relationships.between.objects.aggregation.composition.task13;

import java.util.List;

public class University {
    private List<Department> departments;

    public University addDepartment(Department department) {
        departments.add(department);
        return this;
    }
}