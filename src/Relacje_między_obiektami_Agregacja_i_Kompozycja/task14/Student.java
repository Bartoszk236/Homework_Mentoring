package src.Relacje_między_obiektami_Agregacja_i_Kompozycja.task14;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private List<Course> courses = new ArrayList<>();

    public Student addCourse(Course course) {
        courses.add(course);
        return this;
    }
}
