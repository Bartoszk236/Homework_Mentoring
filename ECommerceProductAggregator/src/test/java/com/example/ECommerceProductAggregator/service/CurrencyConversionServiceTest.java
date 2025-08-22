package com.example.ECommerceProductAggregator.service;

import com.example.ECommerceProductAggregator.client.NbpClient;
import com.example.ECommerceProductAggregator.dto.CurrencyRateResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceTest {
    @Mock
    NbpClient nbpClient;
    @InjectMocks
    CurrencyConversionService service;

    @ParameterizedTest(name = "amount={0}, curr={1}, rate={2} => expected={3}")
    @CsvSource(textBlock = """
                100.00, USD, 3.90,    390.00
                1.005,  EUR, 4.30,      4.32
                99.99,  EUR, 4.1234,  412.30
                0.01,   CHF, 4.5000,    0.05
            """)
    void givenAmountAndRatesWhenConvertToPlnThenReturnExpectedValue(String amountStr, String currency, String rateStr, String expectedStr) {
        // given
        BigDecimal amount = new BigDecimal(amountStr);
        BigDecimal expected = new BigDecimal(expectedStr);

        CurrencyRateResponse.Rates rates = new CurrencyRateResponse.Rates(new BigDecimal(rateStr));
        CurrencyRateResponse response = new CurrencyRateResponse(List.of(rates));
        when(nbpClient.getRates(currency)).thenReturn(response);

        // when
        BigDecimal result = service.convertToPln(amount, currency);

        // then
        assertThat(result).isEqualByComparingTo(expected);
        verify(nbpClient, times(1)).getRates(currency);
        verifyNoMoreInteractions(nbpClient);
    }
}
