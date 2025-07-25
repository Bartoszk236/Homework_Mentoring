package com.example.StreamingAndPerformanceOptimization.service;

import com.example.StreamingAndPerformanceOptimization.entity.Users;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class StreamServiceTest {
    private final UserService userService = Mockito.mock(UserService.class);
    private final ObjectMapper realMapper = new ObjectMapper();
    private final StreamService streamService = new StreamService(realMapper, userService);

    @Test
    void streamShouldProduceGzippedJsonArrayWithCorrectSlice() throws Exception {
        //given
        Users u1 = new Users(1L, "Alice", "password");
        Users u2 = new Users(2L, "Bob", "password");
        Users u3 = new Users(3L, "Carol", "password");

        when(userService.streamAllUsers(2, "filterX"))
                .thenReturn(Stream.of(
                        List.of(u1, u2),
                        List.of(u3)
                ));

        //when
        StreamingResponseBody body = streamService.stream("filterX", 2, 1, 3);

        //decode
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        body.writeTo(baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        GZIPInputStream gzipIn = new GZIPInputStream(bais);
        String json = new String(gzipIn.readAllBytes(), StandardCharsets.UTF_8);

        List<Map<String,Object>> list = realMapper.readValue(json, new TypeReference<>(){});

        //then
        assertThat(list).hasSize(2);
        assertThat(list.get(0).get("id")).isEqualTo(2);    // Bob
        assertThat(list.get(0).get("username")).isEqualTo("Bob");
        assertThat(list.get(1).get("id")).isEqualTo(3);    // Carol
        assertThat(list.get(1).get("username")).isEqualTo("Carol");

        verify(userService).streamAllUsers(2, "filterX");
    }

    @Test
    void streamWithStartBeyondEndShouldReturnEmptyArray() throws Exception {
        //given
        Users u1 = new Users(1L, "Alice",  "password");
        when(userService.streamAllUsers(10, "any"))
                .thenReturn(Stream.of(List.of(u1)));

        //when
        StreamingResponseBody body = streamService.stream("any", 10, 5, 10);

        //decode
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        body.writeTo(baos);
        GZIPInputStream gzipIn = new GZIPInputStream(new ByteArrayInputStream(baos.toByteArray()));
        String json = new String(gzipIn.readAllBytes(), StandardCharsets.UTF_8);

        //then
        assertThat(json).isEqualTo("[]");
    }
}
