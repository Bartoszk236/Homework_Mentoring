package src.typecasting.task9;

public class Student extends Person {
    private int studentId;

    public Student(String name, int studentId) {
        super(name);
        this.studentId = studentId;
    }

    public void getDuty(){
        System.out.println("I'm student and i have to studying");
    }
}