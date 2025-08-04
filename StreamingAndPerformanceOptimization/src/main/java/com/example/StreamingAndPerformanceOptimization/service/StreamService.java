package com.example.StreamingAndPerformanceOptimization.service;

import com.example.StreamingAndPerformanceOptimization.dto.UsersResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamService {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    public StreamingResponseBody stream(String filter, int batchSize, Integer start, Integer end) {
        return outputStream -> {
            try (
                    GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
                    JsonGenerator gen = objectMapper.getFactory().createGenerator(gzip)
            ) {
                gen.writeStartArray();

                userService.streamAllUsers(batchSize, filter)
                        .flatMap(List::stream)
                        .skip(start)
                        .limit(end - start)
                        .forEach(user -> {
                            try {
                                gen.writeObject(UsersResponse.from(user));
                            } catch (IOException e) {
                                log.error("StreamService::stream | error while writing Users", e);
                            }
                        });

                gen.writeEndArray();
                gen.flush();
            } catch (IOException e) {
                log.error("StreamService::stream | error while stream output", e);
            }
        };
    }
}
