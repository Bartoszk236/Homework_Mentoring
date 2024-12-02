package src.string.operation.task10;

public class Main {
    public static void main(String[] args) {
        long firstStartTime = System.nanoTime();
        String first = "Jestem";
        first += " Bartosz";
        first += " i";
        first += " mam";
        first += " 21";
        first += " lat";
        long firstEndTime = System.nanoTime();

        long secondStartTime = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        builder.append("Jestem");
        builder.append(" Bartosz");
        builder.append(" i");
        builder.append(" mam");
        builder.append(" 21");
        builder.append(" lat");
        long secondEndTime = System.nanoTime();

        System.out.println("Efficiency classic: " + (firstEndTime - firstStartTime) + " milliseconds");
        System.out.println("Efficiency String builder: " + (secondEndTime - secondStartTime) + " milliseconds");
    }
}