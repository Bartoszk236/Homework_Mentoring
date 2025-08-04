package com.example.JsonDeserializationWithCustomLogic.deserializer;

import com.example.JsonDeserializationWithCustomLogic.dto.OrderItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

import java.io.IOException;
import java.util.List;

public class OrderItemDeserializer extends JsonDeserializer<List<OrderItem>> {
    @Override
    public List<OrderItem> deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

        TypeReference<List<OrderItem>> typeReference = new TypeReference<>() {};
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonToken jsonToken = jsonParser.getCurrentToken();

        if (jsonToken == JsonToken.VALUE_STRING) {
            String text = jsonParser.getText();
            return mapper.readValue(text, typeReference);
        } else if (jsonToken == JsonToken.START_ARRAY) {
            return mapper.readValue(jsonParser, typeReference);
        } else {
            throw new RuntimeJsonMappingException("Unexpected token " + jsonToken);
        }
    }
}
