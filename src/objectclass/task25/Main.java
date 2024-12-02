package src.objectclass.task25;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("Bartosz", "Kocylo");
        Student student2 = new Student("Bartosz", "Kocylo2");

        HashMap<Integer, Student> students = new HashMap<>();
        students.put(student1.hashCode(), student1);
        students.put(student2.hashCode(), student2);
    }
}