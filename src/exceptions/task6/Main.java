package src.exceptions.task6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/bartoszkocylo/development/Homework_Mentoring/src/exceptions/task6/example.txt"))) {
            String line = br.readLine();
            int number = Integer.parseInt(line);
            System.out.println(number);
        } catch (NumberFormatException | FileNotFoundException e) {
            System.out.println("File not found or text in file is not a number");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}