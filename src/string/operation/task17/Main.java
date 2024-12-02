package src.string.operation.task17;

public class Main {
    public static void main(String[] args) {
        long efficiencyStringBufferTime = efficiencyStringBuffer();
        long efficiencyStringBuilderTime = efficiencyStringBuilder();
        System.out.println("Efficiency StringBuilder: " + efficiencyStringBuilderTime + " nano seconds");
        System.out.println("Efficiency StringBuffer: " + efficiencyStringBufferTime + " nano seconds");
        System.out.println("Better is: " + (efficiencyStringBufferTime > efficiencyStringBuilderTime ? "StringBuilder" : "StringBuffer"));
    }

    public static long efficiencyStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append("New sentence number: " + i + " create by stringBuilder: " + i);
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static long efficiencyStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            stringBuffer.append("New sentence number: " + i + " create by StringBuffer");
        }
        long end = System.nanoTime();
        return end - start;
    }
}