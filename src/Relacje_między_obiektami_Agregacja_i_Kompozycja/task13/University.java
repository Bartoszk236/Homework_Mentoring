package src.Relacje_między_obiektami_Agregacja_i_Kompozycja.task13;

import java.util.List;

public class University {
    private List<Department> departments;

    public University addDepartment(Department department) {
        departments.add(department);
        return this;
    }
}
