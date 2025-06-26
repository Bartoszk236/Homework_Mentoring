package com.example.JsonDeserializationWithCustomLogic.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DeliveryDateDeserializerTest {
    private final DeliveryDateDeserializer deliveryDateDeserializer = new DeliveryDateDeserializer();
    private final JsonParser jsonParser = Mockito.mock(JsonParser.class);

    @Test
    void givenDateInFirstPatternWhenDeserializationThenReturnDeliveryDate() throws Exception {
        //given
        String date = "2020-01-23";
        Mockito.when(jsonParser.getCurrentToken()).thenReturn(JsonToken.VALUE_STRING);
        Mockito.when(jsonParser.getText()).thenReturn(date);

        //when
        LocalDate result = deliveryDateDeserializer.deserialize(jsonParser, null);

        //then
        Assertions.assertEquals(LocalDate.of(2020, 1, 23), result);
    }

    @Test
    void givenDateInSecondPatternWhenDeserializationThenReturnDeliveryDate() throws Exception {
        //given
        String date = "23/01/2023";
        Mockito.when(jsonParser.getCurrentToken()).thenReturn(JsonToken.VALUE_STRING);
        Mockito.when(jsonParser.getText()).thenReturn(date);

        //when
        LocalDate result = deliveryDateDeserializer.deserialize(jsonParser, null);

        //then
        Assertions.assertEquals(LocalDate.of(2023, 1, 23), result);
    }

    @Test
    void givenDateInTimeStampWhenDeserializationThenReturnDeliveryDate() throws Exception {
        //given
        Long timestamp = 1710000000000L;
        Mockito.when(jsonParser.getCurrentToken()).thenReturn(JsonToken.VALUE_NUMBER_INT);
        Mockito.when(jsonParser.getLongValue()).thenReturn(timestamp);

        //when
        LocalDate result = deliveryDateDeserializer.deserialize(jsonParser, null);

        //then
        Assertions.assertEquals(LocalDate.of(2024, 3, 9), result);
    }

    @Test
    void givenInvalidDateWhenDeserializationThenThrowException() {
        //given
        Mockito.when(jsonParser.getCurrentToken()).thenReturn(null);

        //when/then
        RuntimeJsonMappingException ex = assertThrows(RuntimeJsonMappingException.class,
                () -> deliveryDateDeserializer.deserialize(jsonParser, null));
        assertTrue(ex.getMessage().contains("Unsupported JSON token: "));
    }
}
