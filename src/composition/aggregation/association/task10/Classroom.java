package src.composition.aggregation.association.task10;

import java.util.List;

public class Classroom {
    private List<Student> students;

    public Classroom addStudent(Student student) {
        students.add(student);
        return this;
    }
}