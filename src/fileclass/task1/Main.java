package src.fileclass.task1;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("/Users/bartoszkocylo/development/Homework_Mentoring/example.txt");
        boolean exists = file.exists();
        System.out.println(exists);
        String fileName = file.getName();
        String path = file.getAbsolutePath();
        String parent = file.getParent();
        System.out.println("fileName = " + fileName);
        System.out.println("path = " + path);
        System.out.println("parent = " + parent);
        boolean isDirectory = file.isDirectory();
        String answer = isDirectory ? "directory" : "file";
        System.out.println("This is: " + answer);
        boolean wasCreated = new File("/Users/bartoszkocylo/development/Homework_Mentoring/directory").mkdir();
        String answer2 = wasCreated ? "create successful" : "directory exist or error while creating";
        System.out.println(answer2);
    }
}
