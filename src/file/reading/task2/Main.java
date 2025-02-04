package src.file.reading.task2;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("/Users/bartoszkocylo/development/Homework_Mentoring/example.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(path.toFile());
            while (fileInputStream.available() > 0) {
                System.out.print((char) fileInputStream.read());
            }
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
