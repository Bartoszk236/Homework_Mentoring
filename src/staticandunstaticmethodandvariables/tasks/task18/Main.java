package src.staticandunstaticmethodandvariables.tasks.task18;

import static src.staticandunstaticmethodandvariables.tasks.task18.MathConstants.*;

public class Main {

    public static double getCircleArea(double radius) {
        return PI * radius * radius;
    }

    public static double calculateProfits(double startAmount, double percentAPR, double timeInYears){
        double APR = percentAPR / 100;
        double x = APR * timeInYears;
        return startAmount * Math.pow(E, x);
    }

    public static void main(String[] args) {
        System.out.println(getCircleArea(15));
        System.out.println(calculateProfits(100.0, 6.0, 2.0));
    }
}