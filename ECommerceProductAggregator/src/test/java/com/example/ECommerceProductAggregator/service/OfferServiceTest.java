package com.example.ECommerceProductAggregator.service;

import com.example.ECommerceProductAggregator.client.EuroNetClient;
import com.example.ECommerceProductAggregator.client.MediaExpertClient;
import com.example.ECommerceProductAggregator.client.MediaMarktClient;
import com.example.ECommerceProductAggregator.dto.ExternalApiResponse;
import com.example.ECommerceProductAggregator.dto.Product;
import com.example.ECommerceProductAggregator.exception.ProductNotFoundException;
import com.example.ECommerceProductAggregator.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {
    @Mock
    EuroNetClient euroNetClient;
    @Mock
    MediaExpertClient mediaExpertClient;
    @Mock
    MediaMarktClient mediaMarktClient;
    @Mock
    CurrencyConversionService currencyConversionService;
    @Spy
    ProductMapper mapper = new ProductMapper();
    @InjectMocks
    OfferService offerService;

    @Test
    void givenEmptySourceWhenFindBestOffersThenReturnProductWithIdSearchingNameDefaultDescriptionAndEmptyOffers() {
        //given
        String product = "Xbox";
        when(euroNetClient.getProduct(product)).thenReturn(List.of());
        when(mediaExpertClient.getProduct(product)).thenReturn(null);
        when(mediaMarktClient.getProduct(product)).thenReturn(List.of());

        //when
        Product out = offerService.findBestOffers(product);

        //then
        InOrder inOrder = inOrder(euroNetClient, mediaExpertClient, mediaMarktClient);
        inOrder.verify(euroNetClient).getProduct(product);
        inOrder.verify(mediaExpertClient).getProduct(product);
        inOrder.verify(mediaMarktClient).getProduct(product);

        assertThat(out).isNotNull();
        assertThat(out.name()).isEqualTo(product);
        assertThat(out.offers()).isEmpty();
        assertThat(out.description()).isEqualTo("Description unavailable");
    }

    @Test
    void givenSourceWithDifferentCurrenciesWhenFindBestOffersThenReturnProductWithOffersWithNewPriceAndPLN() {
        //given
        String product = "Xbox";

        var rUsd = resp(1L, "120", "USD", true, "ShopB");
        var rEur = resp(2L, "100", "eur", true, "ShopC");
        var rPln = resp(3L, "500", "PLN", true, "ShopA");

        when(euroNetClient.getProduct(product)).thenReturn(List.of(rUsd, rEur, rPln));
        when(mediaExpertClient.getProduct(product)).thenReturn(List.of());
        when(mediaMarktClient.getProduct(product)).thenReturn(List.of());

        when(currencyConversionService.convertToPln(new BigDecimal("120"), "USD"))
                .thenReturn(new BigDecimal("480"));
        when(currencyConversionService.convertToPln(new BigDecimal("100"), "eur"))
                .thenReturn(new BigDecimal("430"));

        //when
        Product out = offerService.findBestOffers(product);

        //then
        assertThat(out.offers()).hasSize(3);

        var usd = out.offers()
                .stream()
                .filter(o -> o.getShopName().equals("ShopB"))
                .findFirst()
                .orElseThrow();
        var eur = out.offers()
                .stream()
                .filter(o -> o.getShopName().equals("ShopC"))
                .findFirst()
                .orElseThrow();
        var pln = out.offers()
                .stream()
                .filter(o -> o.getShopName().equals("ShopA"))
                .findFirst()
                .orElseThrow();

        assertThat(usd.getCurrency()).isEqualTo("PLN");
        assertThat(usd.getPrice()).isEqualByComparingTo("480");

        assertThat(eur.getCurrency()).isEqualTo("PLN");
        assertThat(eur.getPrice()).isEqualByComparingTo("430");

        assertThat(pln.getCurrency()).isEqualTo("PLN");
        assertThat(pln.getPrice()).isEqualByComparingTo("500");

        verify(currencyConversionService).convertToPln(new BigDecimal("120"), "USD");
        verify(currencyConversionService).convertToPln(new BigDecimal("100"), "eur");
        verify(currencyConversionService, never()).convertToPln(any(), eq("PLN"));
    }

    @Test
    void givenProductOffersWithTheSameCurrencyWhenFindBestOffersThenReturnSortedOffers() {
        //given
        String product = "Xbox";

        var r1 = resp(1L, "500", "PLN", true, "B-Shop");
        var r2 = resp(2L, "400", "PLN", true, "A-Shop");
        var r3 = resp(3L, "50", "PLN", false, "C-Shop");

        when(euroNetClient.getProduct(product)).thenReturn(List.of(r1, r2, r3));
        when(mediaExpertClient.getProduct(product)).thenReturn(List.of());
        when(mediaMarktClient.getProduct(product)).thenReturn(List.of());

        //when
        Product out = offerService.findBestOffers(product);

        //then
        assertThat(out.offers()).extracting(o -> List.of(o.getShopName(), o.getPrice(), o.getInStock()))
                .containsExactly(
                        List.of("A-Shop", new BigDecimal("400"), true),
                        List.of("B-Shop", new BigDecimal("500"), true),
                        List.of("C-Shop", new BigDecimal("50"), false)
                );

        verify(currencyConversionService, never()).convertToPln(any(), anyString());
    }

    @Test
    void givenProductNameAndExceptionFromClientWhenFindBestOffersThenVerifyNoInteractions() {
        //given
        String product = "Xbox";
        when(euroNetClient.getProduct(product)).thenThrow(new ProductNotFoundException("x"));

        //when / then
        assertThatThrownBy(() -> offerService.findBestOffers(product))
                .isInstanceOf(ProductNotFoundException.class);

        verifyNoInteractions(mediaExpertClient, mediaMarktClient, currencyConversionService);
        verify(mapper, never()).toProductOffers(anyList());
    }

    private ExternalApiResponse resp(long id,
                                     String price, String currency,
                                     boolean inStock, String shop) {
        return new ExternalApiResponse(
                id,
                "Xbox",
                null,
                new BigDecimal(price),
                currency,
                inStock,
                shop,
                List.of()
        );
    }
}
