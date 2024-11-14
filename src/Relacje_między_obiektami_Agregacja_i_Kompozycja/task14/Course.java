package src.Relacje_między_obiektami_Agregacja_i_Kompozycja.task14;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private List<Student> students = new ArrayList<>();

    public Course addStudent(Student student) {
        students.add(student);
        return this;
    }
}
