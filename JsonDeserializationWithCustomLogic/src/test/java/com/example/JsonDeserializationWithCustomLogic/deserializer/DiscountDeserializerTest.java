package com.example.JsonDeserializationWithCustomLogic.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DiscountDeserializerTest {
    private final DiscountDeserializer discountDeserializer = new DiscountDeserializer();
    private final JsonParser jsonParser = Mockito.mock(JsonParser.class);

    @Test
    void givenNullPraserWhenDeserializedThenReturnZero() throws IOException {
        //when
        BigDecimal result = discountDeserializer.deserialize(null, null);

        //then
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void givenValidPercentAmountWhenDeserializedThenReturnBigDecimalAmount() throws IOException {
        //given
        String discountPercent = "10%";
        Mockito.when(jsonParser.getText()).thenReturn(discountPercent);
        Mockito.when(jsonParser.getCurrentToken()).thenReturn(JsonToken.VALUE_STRING);

        //when
        BigDecimal result = discountDeserializer.deserialize(jsonParser, null);

        //then
        assertEquals(new BigDecimal("0.10"), result);
    }

    @Test
    void givenValidDiscountAmountWhenDeserializedThenReturnBigDecimalAmount() throws IOException {
        //given
        String discountAmount = "10.00";
        Mockito.when(jsonParser.getText()).thenReturn(discountAmount);
        Mockito.when(jsonParser.getCurrentToken()).thenReturn(JsonToken.VALUE_STRING);

        //when
        BigDecimal result = discountDeserializer.deserialize(jsonParser, null);

        //then
        assertEquals(new BigDecimal("10.00"), result);
    }

    @Test
    void givenInvalidTypeOfJsonPraserWhenDeserializedThenThrowException() throws IOException {
        //given
        Mockito.when(jsonParser.getText()).thenReturn(null);
        Mockito.when(jsonParser.getCurrentToken()).thenReturn(null);

        //when/then
        RuntimeJsonMappingException ex = assertThrows(RuntimeJsonMappingException.class,
                () -> discountDeserializer.deserialize(jsonParser, null));
        assertTrue(ex.getMessage().contains("Expected string but got "));
    }
}
