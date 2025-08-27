package com.MultiTenantUserManagementSystem.decoder;

import com.MultiTenantUserManagementSystem.exception.ExternalApiException;
import feign.FeignException;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class CustomErrorDecoderTest {
    private final CustomErrorDecoder customErrorDecoder = new CustomErrorDecoder();

    @Test
    void givenResponseWithMessageWhenDecodeThenReturnExceptionWithMessage() {
        //given
        Response response = createResponse(
                409,
                "{\"message\":\"EMAIL_EXISTS\"}"
        );

        //when
        Exception exception = customErrorDecoder.decode("Client#createUser", response);

        //then
        assertThat(exception)
                .isInstanceOf(ExternalApiException.class)
                .hasMessage("EMAIL_EXISTS");
    }

    @Test
    void givenResponseWithEmptyBodyWhenDecodeThenReturnExceptionWithDefaultMessage() {
        //given
        Response response = createResponse(400, "{}");

        //when
        Exception exception = customErrorDecoder.decode("Client#createUser", response);

        //then
        assertThat(exception)
                .isInstanceOf(ExternalApiException.class)
                .hasMessage("failed to capture message from external API");
    }

    @Test
    void givenNotJsonBodyWhenDecodeThenReturnExceptionWithDefaultMessage() {
        //given
        Response response = createResponse(500, "<html>oops</html>");

        //when
        Exception exception = customErrorDecoder.decode("Client#createUser", response);

        //then
        assertThat(exception).isInstanceOf(FeignException.class);
        assertThat(((FeignException) exception).status())
                .isEqualTo(500);
    }

    @Test
    void givenResponseWithNoBodyWhenDecodeThenReturnExceptionWithDefaultMessage() {
        //given
        Response response = createResponseNoBody();

        //when
        Exception exception = customErrorDecoder.decode("Client#createUser", response);

        assertThat(exception).isInstanceOf(FeignException.class);
        assertThat(((FeignException) exception).status()).isEqualTo(404);
    }

    private Response createResponse(int status, String message) {
        Request request = createRequest();
        return Response.builder()
                .status(status)
                .reason("X")
                .request(request)
                .body(message, StandardCharsets.UTF_8)
                .build();
    }

    private Response createResponseNoBody() {
        Request request = createRequest();
        return Response.builder()
                .status(404)
                .reason("X")
                .request(request)
                .build();
    }

    private Request createRequest() {
        return Request.create(
                Request.HttpMethod.POST,
                "http://localhost:8080",
                Map.of(),
                null,
                StandardCharsets.UTF_8,
                null
        );
    }
}
