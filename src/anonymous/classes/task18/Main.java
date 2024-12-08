package src.anonymous.classes.task18;

public class Main {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("Run1");
            }
        };
        runnable.run();
        Runnable runnable1 = new Runnable() {
            public void run() {
                System.out.println("Run2");
            }
        };
        runnable1.run();
    }
}