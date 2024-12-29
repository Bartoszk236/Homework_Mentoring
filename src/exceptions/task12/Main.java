package src.exceptions.task12;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(checkEligibility(19));
        } catch (IllegalStateException e) {
            System.out.println("you not enable, you have less then 18 years");
        } catch (IllegalArgumentException e) {
            System.out.println("you put invalid age");
        }
    }

    public static boolean checkEligibility(int age) {
        if (age < 18) throw new IllegalStateException();
        if (age > 120) throw new IllegalArgumentException();
        return true;
    }
}