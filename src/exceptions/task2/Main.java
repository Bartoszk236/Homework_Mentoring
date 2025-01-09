package src.exceptions.task2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println(readFile("src/exceptions/task2/input.txt"));
    }

    public static String readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            return br.readLine();
        } catch (FileNotFoundException e) {
            return "No such file: " + fileName;
        } catch (IOException e) {
            return "Error reading file: " + fileName;
        }
    }
}