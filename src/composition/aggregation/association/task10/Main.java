package src.composition.aggregation.association.task10;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();
        Classroom classroom = new Classroom();
        School school = new School();
        classroom.addStudent(student);
        school.addClassroom(classroom);
    }
}