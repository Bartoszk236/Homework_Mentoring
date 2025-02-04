package src.files.properties.task6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("/Users/bartoszkocylo/development/Homework_Mentoring/example.txt");
        Path destination = Paths.get("/Users/bartoszkocylo/development/Homework_Mentoring/copy.txt");
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.forEach(System.out::println);
            Files.copy(path, destination);
            Files.deleteIfExists(destination);
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        }

        Properties properties = new Properties();
        try (FileOutputStream fileOutputStream = new FileOutputStream("config.properties")){
            properties.store(fileOutputStream, "Create properties file");
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        }

        try (FileInputStream fileInputStream = new FileInputStream("config.properties")){
            properties.load(fileInputStream);
            properties.setProperty("user", "bartoszkocylo");
            properties.setProperty("password", "1234");
            FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
            properties.store(fileOutputStream, "user properties");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
