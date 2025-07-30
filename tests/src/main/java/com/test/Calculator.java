package com.test;

public class Calculator {
    public double calculate(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                throw new RuntimeException("Invalid operator: " + operator);
        }
    }
}
