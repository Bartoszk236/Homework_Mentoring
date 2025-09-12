package com.example.ECommerceProductAggregator.client;

import com.example.ECommerceProductAggregator.dto.ExternalApiResponse;
import com.example.ECommerceProductAggregator.exception.ProductNotFoundException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class MediaMarktClientTest {
    private static final String BASE_URL = "http://localhost:8080/MediaMarkt";
    private MediaMarktClient mediaMarktClient;
    private MockRestServiceServer mockServer;
    private String productName;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl(BASE_URL);

        mockServer = MockRestServiceServer.bindTo(builder).build();

        RestClient restClient = builder.build();
        mediaMarktClient = new MediaMarktClient(restClient);
        productName = "Xbox";
    }

    @Test
    void givenValidResponseWhenGetProductThenReturnProduct() {
        //given
        mockServer.expect(requestTo(BASE_URL + "/" + productName))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(ExternalApiResponses.getValidResponseFromMediaMarkt(), MediaType.APPLICATION_JSON));

        //when
        List<ExternalApiResponse> result = mediaMarktClient.getProduct(productName);

        //then
        assertThat(result).hasSize(1);
        AssertionsForClassTypes.assertThat(result.getFirst().name()).isEqualTo(productName);
        mockServer.verify();
    }

    @Test
    void givenErrorResponseWhenGetProductThenThrowProductNotFoundException() {
        //given
        mockServer.expect(requestTo(BASE_URL + "/" + productName))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        //when / then
        assertThatThrownBy(() -> mediaMarktClient.getProduct(productName))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product: " + productName + "not found");
    }
}
