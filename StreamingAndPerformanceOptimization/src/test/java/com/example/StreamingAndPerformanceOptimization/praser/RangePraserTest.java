package com.example.StreamingAndPerformanceOptimization.praser;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RangePraserTest {
    private final RangePraser rangePraser = new RangePraser();

    @Test
    void givenValidUserRangeWhenRangeToValueThenReturnValidValues() {
        //given
        String validUserRange = "users=0-10";

        //when
        Map<String, Integer> result = rangePraser.rangeToValue(validUserRange);

        //then
        assertEquals(0,   result.get("start"));
        assertEquals(10,  result.get("end"));
    }

    @Test
    void givenUserRangeWithoutPrefixWhenRangeToValueThenThrowIllegalArgumentException() {
        //given
        String userRangeWithoutPrefix = "0-10";

        //when/then
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue(userRangeWithoutPrefix)
        );
        assertEquals("users range must start with 'users='", ex.getMessage());
    }

    @Test
    void givenUserRangeWithBadFormatWhenRangeToValueThenThrowInvalidFormat() {
        //given
        String expectedMessage = "invalid users range format";

        // missing dash
        IllegalArgumentException ex1 = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue("users=010")
        );
        assertEquals(expectedMessage, ex1.getMessage());

        // too many parts
        IllegalArgumentException ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue("users=1-2-3")
        );
        assertEquals(expectedMessage, ex2.getMessage());

        // letters
        IllegalArgumentException ex3 = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue("users=a-b")
        );
        assertEquals(expectedMessage, ex3.getMessage());

        // leading/trailing spaces
        IllegalArgumentException ex4 = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue("users= 0-10")
        );
        assertEquals(expectedMessage, ex4.getMessage());
        IllegalArgumentException ex5 = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue("users=0-10 ")
        );
        assertEquals(expectedMessage, ex5.getMessage());
    }

    @Test
    void givenUserRangeWithEqualStartAndEndValueWhenRangeToValueThenThrowIllegalArgumentException() {
        //given
        String userRangeWithEqualStartAndEndValue = "users=5-5";

        //when/then
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue(userRangeWithEqualStartAndEndValue)
        );
        assertEquals("end value cannot be less or equal than start value", ex.getMessage());
    }

    @Test
    void givenUserRangeWithStartGreaterThanEndValueWhenRangeToValueThenThrowIllegalArgumentException() {
        //given
        String userRangeWithStartGreaterThanEndValue = "users=10-5";

        //when/then
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> rangePraser.rangeToValue(userRangeWithStartGreaterThanEndValue)
        );
        assertEquals("end value cannot be less or equal than start value", ex.getMessage());
    }
}
