package src.exceptions.task11;

public class Main {
    public static void main(String[] args) {
        try {
            openFile();
            System.out.println("Open file successfully");
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("close file");
        }
    }

    public static void openFile(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}