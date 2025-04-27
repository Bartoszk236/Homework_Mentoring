package com.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(2.2, 3.3, '+', 5.5),
                Arguments.of(7.1, 2.9, '+', 10.0),
                Arguments.of(4.4, 5.6, '+', 10.0),
                Arguments.of(9.9, 0.1, '+', 10.0),
                Arguments.of(10.5, 2.5, '-', 8.0),
                Arguments.of(20.0, 5.5, '-', 14.5),
                Arguments.of(7.7, 7.2, '-', 0.5),
                Arguments.of(15.0, 3.0, '-', 12.0),
                Arguments.of(2.0, 3.5, '*', 7.0),
                Arguments.of(4.0, 2.5, '*', 10.0),
                Arguments.of(3.3, 3.3, '*', 10.89),
                Arguments.of(5.5, 2.0, '*', 11.0),
                Arguments.of(9.0, 3.0, '/', 3.0),
                Arguments.of(10.0, 2.5, '/', 4.0),
                Arguments.of(8.4, 2.1, '/', 4.0),
                Arguments.of(7.5, 2.5, '/', 3.0)
        );
    }
    private final Calculator calculator;

    CalculatorTest() {
        this.calculator = new Calculator();
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void calculatorTestFromStream(double a, double b, char operator, double expected) {
        //when
        double actual = calculator.calculate(a, b, operator);
        //then (tolerancja 0.000001, z racji używania typu prymitywnego)
        assertEquals(expected, actual, 1e-6);
    }

    @ParameterizedTest
    @CsvFileSource(
            resources = "/provide_values.csv",
            numLinesToSkip = 1,
            delimiter = ','
    )
    void calculatorTestFromCSV(double a, double b, char operator, double expected) {
        //when
        double actual = calculator.calculate(a, b, operator);
        //then (tolerancja 0.000001, z racji używania typu prymitywnego)
        assertEquals(expected, actual, 1e-6);
    }
}
