package src.exceptions.task5;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (
                CustomResource resource1 = new CustomResource("resource1");
                CustomResource resource2 = new CustomResource("resource2");
                CustomResource resource3 = new CustomResource("resource3")
        ) {

            resource1.run();
            resource2.run();
            resource3.run();

        } catch (Exception mainException) {
            System.out.println("Main exception: " + mainException.getMessage());
            List<Throwable> exceptions = List.of(mainException.getSuppressed());
            exceptions.forEach(exception -> System.out.println("Suppressed exception: " + exception.getMessage()));
        }
    }
}