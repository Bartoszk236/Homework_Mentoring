package com.example.JsonDeserializationWithCustomLogic.deserializer;

import com.example.JsonDeserializationWithCustomLogic.dto.OrderItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderItemDeserializerTest {
    private final OrderItemDeserializer orderItemDeserializer = new OrderItemDeserializer();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenValidItemsArrayWhenDeserializeThenReturnListOfItems() throws IOException {
        //given
        String rawArray = "["
                + "{\"productId\":1,\"quantity\":1,\"unitPrice\":50.00},"
                + "{\"productId\":2,\"quantity\":2,\"unitPrice\":25.00}"
                + "]";
        JsonParser parser = objectMapper.getFactory().createParser(rawArray);

        //when
        assertEquals(JsonToken.START_ARRAY, parser.nextToken());
        List<OrderItem> items = orderItemDeserializer.deserialize(parser, null);

        //then
        assertEquals(2, items.size());
        assertEquals(1, items.get(0).productId());
        assertEquals(0,
                items.get(0).unitPrice().compareTo(new BigDecimal("50")));
        assertEquals(2, items.get(1).productId());
        assertEquals(2, items.get(1).quantity());
    }

    @Test
    void givenValidItemsStringWhenDeserializeThenReturnListOfItems() throws IOException {
        //given
        String rawArray = "["
                + "{\"productId\":1,\"quantity\":1,\"unitPrice\":50.00},"
                + "{\"productId\":2,\"quantity\":2,\"unitPrice\":25.00}"
                + "]";
        String wrapped = objectMapper.writeValueAsString(rawArray);
        JsonParser parser = objectMapper.getFactory().createParser(wrapped);

        //when
        assertEquals(JsonToken.VALUE_STRING, parser.nextToken());
        List<OrderItem> items = orderItemDeserializer.deserialize(parser, null);

        //then
        assertEquals(2, items.size());
        assertEquals(1, items.getFirst().productId());
        assertEquals(0,
                items.getFirst().unitPrice().compareTo(new BigDecimal("50")));
    }

    @Test
    void givenInvalidFormatWhenDeserializeThenThrowException() throws IOException {
        //given
        String invalid = "{\"invalid\":123}";
        String wrapped = objectMapper.writeValueAsString(invalid);
        JsonParser parser = objectMapper.getFactory().createParser(wrapped);

        //when/then
        RuntimeJsonMappingException ex = assertThrows(RuntimeJsonMappingException.class,
                () -> orderItemDeserializer.deserialize(parser, null));
        assertTrue(ex.getMessage().contains("Unexpected token"));
    }
}
