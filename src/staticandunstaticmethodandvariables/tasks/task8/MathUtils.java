package src.staticandunstaticmethodandvariables.tasks.task8;

public final class MathUtils {
    private MathUtils() {}
    public static int compareNumbers(int a, int b){
        return (a < b) ? b : a;
    }
}