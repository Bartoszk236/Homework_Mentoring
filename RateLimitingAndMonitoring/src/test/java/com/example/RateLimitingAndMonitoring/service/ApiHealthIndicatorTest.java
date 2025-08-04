package com.example.RateLimitingAndMonitoring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ApiHealthIndicator.class)
class ApiHealthIndicatorTest {
    ApiHealthIndicator apiHealthIndicator;
    MockRestServiceServer mockServer;

    @Autowired
    public ApiHealthIndicatorTest(ApiHealthIndicator apiHealthIndicator, MockRestServiceServer mockServer) {
        this.apiHealthIndicator = apiHealthIndicator;
        this.mockServer = mockServer;
    }

    @Test
    void givenResponseWithStatusOKAndBodyWhenHealthThenReturnStatusUp() {
        //given
        mockServer
                .expect(requestTo("http://localhost:8080/test"))
                .andRespond(withSuccess("OK", MediaType.TEXT_PLAIN));

        //when
        Health health = apiHealthIndicator.health();

        //then
        assertEquals(Status.UP, health.getStatus());
        assertEquals("/test reachable", health.getDetails().get("external"));
        assertEquals(200, health.getDetails().get("status"));
        mockServer.verify();
    }

    @Test
    void givenResponseWithStatusOKAndEmptyBodyWhenHealthThenReturnStatusDown() {
        //given
        mockServer
                .expect(requestTo("http://localhost:8080/test"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN));

        //when
        Health health = apiHealthIndicator.health();

        //then
        assertEquals(Status.DOWN, health.getStatus());
        mockServer.verify();
    }

    @Test
    void givenResponseWithStatusErrorWhenHealthThenReturnStatusDown() {
        //given
        mockServer
                .expect(requestTo("http://localhost:8080/test"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        //when
        Health health = apiHealthIndicator.health();

        //then
        assertEquals(Status.DOWN, health.getStatus());
        mockServer.verify();
    }

    @Test
    void givenResponseWithFailWhenHealthThenReturnStatusDown() {
        //given
        mockServer
                .expect(requestTo("http://localhost:8080/test"))
                .andRespond(request -> { throw new RestClientException("fail"); });

        //when
        Health health = apiHealthIndicator.health();

        //then
        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("unreachable", health.getDetails().get("external"));
        mockServer.verify();
    }
}
