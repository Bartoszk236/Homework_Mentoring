package src.file.reading.task3;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("/Users/bartoszkocylo/development/Homework_Mentoring/example.txt");
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            int content;
            while ((content = fileInputStream.read()) != -1) {
                System.out.print((char) content);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
