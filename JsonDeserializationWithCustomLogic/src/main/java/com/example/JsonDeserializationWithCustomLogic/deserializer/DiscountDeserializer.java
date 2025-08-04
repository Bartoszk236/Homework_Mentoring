package com.example.JsonDeserializationWithCustomLogic.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountDeserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser == null) return BigDecimal.ZERO;

        String value = jsonParser.getText();

        if (jsonParser.getCurrentToken() != JsonToken.VALUE_STRING)
            throw new RuntimeJsonMappingException("Expected string but got " + value);

        if (value.endsWith("%")) {
            return new BigDecimal(value.replace("%", ""))
                    .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        } else {
            return new BigDecimal(value);
        }
    }
}
