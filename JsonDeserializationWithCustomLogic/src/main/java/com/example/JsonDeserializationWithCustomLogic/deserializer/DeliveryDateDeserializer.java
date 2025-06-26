package com.example.JsonDeserializationWithCustomLogic.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DeliveryDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter FIRST_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter SECOND_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken jsonToken = jsonParser.getCurrentToken();

        if (jsonToken == JsonToken.VALUE_STRING) {
            String value = jsonParser.getText();
            try {
                 return LocalDate.parse(value, FIRST_PATTERN);
            } catch (DateTimeParseException ignored) {}

            try {
                value = value.replace('/', '-');
                return LocalDate.parse(value, SECOND_PATTERN);
            } catch (DateTimeParseException ignored) {}

            try {
                long timestamp = Long.parseLong(value);
                return Instant.ofEpochMilli(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            } catch (NumberFormatException exception) {
                log.error("Error parsing date", exception);
            }
        } else if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            long timestamp = jsonParser.getLongValue();
            return Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        log.error("Error parsing date");
        throw new RuntimeJsonMappingException("Unsupported JSON token: " + jsonToken);
    }
}
