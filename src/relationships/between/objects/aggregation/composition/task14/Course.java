package src.relationships.between.objects.aggregation.composition.task14;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private List<Student> students = new ArrayList<>();

    public Course addStudent(Student student) {
        students.add(student);
        return this;
    }
}