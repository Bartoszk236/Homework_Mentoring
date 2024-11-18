package src.relationships.between.objects.aggregation.composition.task14;

public class Main {
    public static void main(String[] args) {
        Course course = new Course();
        Student student = new Student();
        Student student2 = new Student();
        course.addStudent(student).addStudent(student2);
        student.addCourse(course);
    }
}