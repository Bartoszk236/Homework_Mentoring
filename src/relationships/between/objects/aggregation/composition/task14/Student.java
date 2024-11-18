package src.relationships.between.objects.aggregation.composition.task14;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private List<Course> courses = new ArrayList<>();

    public Student addCourse(Course course) {
        courses.add(course);
        return this;
    }
}