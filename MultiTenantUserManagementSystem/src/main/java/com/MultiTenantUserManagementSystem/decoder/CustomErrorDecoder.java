package com.MultiTenantUserManagementSystem.decoder;

import com.MultiTenantUserManagementSystem.exception.ExternalApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        int responseCode = response.status();
        String messageFromBody;
        try {
            if (response.body() != null) {
                byte[] bytes = response.body().asInputStream().readAllBytes();
                JsonNode root = objectMapper.readTree(bytes);
                if (root.has("message")) {
                    messageFromBody = root.get("message").asText();
                } else {
                    messageFromBody = "failed to capture message from external API";
                }
                return new ExternalApiException(messageFromBody, responseCode);
            }
        } catch (Exception ignored) {

        }
        log.error("error while comparing message from external api");
        return defaultErrorDecoder.decode(s, response);
    }
}
