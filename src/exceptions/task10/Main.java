package src.exceptions.task10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(convertText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        return br.readLine();
    }

    public static String convertText() throws IOException {
        String text = readFile("xyz");
        return text.replaceAll("[^a-zA-Z]", "");
    }
}