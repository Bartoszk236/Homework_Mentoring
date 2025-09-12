package com.example.ECommerceProductAggregator.client;

import com.example.ECommerceProductAggregator.dto.CurrencyRateResponse;
import com.example.ECommerceProductAggregator.exception.CurrencyRatesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class NbpClientTest {
    private NbpClient client;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("https://api.nbp.pl/api/exchangerates/rates/a");

        mockServer = MockRestServiceServer.bindTo(builder).build();

        RestClient restClient = builder.build();
        client = new NbpClient(restClient);
    }

    @Test
    void givenValidResponseWhenGetRatesThenReturnCurrencyRateResponse() {
        //given
        String currencyCode = "USD";
        mockServer.expect(requestTo("https://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(ExternalApiResponses.getValidResponseFromNbpApi(), MediaType.APPLICATION_JSON));

        //when
        CurrencyRateResponse response = client.getRates(currencyCode);

        //then
        assertThat(response).isNotNull();
        assertEquals(new BigDecimal("3.6786"), response.getRate());
        mockServer.verify();
    }

    @Test
    void givenInvalidResponseWhenGetRatesThenThrowCurrencyRatesException() {
        //given
        String currencyCode = "USD";
        mockServer.expect(requestTo("https://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        //when
        assertThatThrownBy(() -> client.getRates(currencyCode))
                .isInstanceOf(CurrencyRatesException.class)
                .hasMessage("Error while retrieving currency rates from Nbp API");
    }
}
