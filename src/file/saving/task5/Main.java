package src.file.saving.task5;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("example.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Hello World");
            bufferedWriter.close();
            FileReader fileReader = new FileReader("example.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
