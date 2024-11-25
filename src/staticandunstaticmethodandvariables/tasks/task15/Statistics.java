package src.staticandunstaticmethodandvariables.tasks.task15;

public class Statistics {
    public static double calculateAverage(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return (double) sum / numbers.length;
    }
}