package com.example.ECommerceProductAggregator.service;

import com.example.ECommerceProductAggregator.client.EuroNetClient;
import com.example.ECommerceProductAggregator.client.MediaExpertClient;
import com.example.ECommerceProductAggregator.client.MediaMarktClient;
import com.example.ECommerceProductAggregator.dto.ExternalApiResponse;
import com.example.ECommerceProductAggregator.dto.Product;
import com.example.ECommerceProductAggregator.dto.ProductOffer;
import com.example.ECommerceProductAggregator.dto.Review;
import com.example.ECommerceProductAggregator.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OfferService {
    public static final String DEFAULT_CURRENCY_CODE = "PLN";
    private final EuroNetClient euroNetClient;
    private final MediaExpertClient mediaExpertClient;
    private final MediaMarktClient mediaMarktClient;
    private final ProductMapper mapper;
    private final CurrencyConversionService currencyConversionService;

    public Product findBestOffers(String productName) {
        List<ExternalApiResponse> euroNetResponses = euroNetClient.getProduct(productName);
        List<ExternalApiResponse> mediaExpertResponses = mediaExpertClient.getProduct(productName);
        List<ExternalApiResponse> mediaMarktResponses = mediaMarktClient.getProduct(productName);

        List<ExternalApiResponse> externalApiResponses = Stream.of(
                        euroNetResponses, mediaExpertResponses, mediaMarktResponses)
                .filter(list -> list != null && !list.isEmpty())
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<ProductOffer> productOffers = mapper.toProductOffers(externalApiResponses);

        productOffers.stream()
                .filter(productOffer -> !productOffer.getCurrency().equalsIgnoreCase(DEFAULT_CURRENCY_CODE))
                .forEach(productOffer -> {
                    String currency = productOffer.getCurrency();
                    BigDecimal price = productOffer.getPrice();
                    BigDecimal newPrice = currencyConversionService.convertToPln(price, currency);
                    productOffer.setPrice(newPrice);
                    productOffer.setCurrency(DEFAULT_CURRENCY_CODE);
                });

        productOffers.sort(Comparator
                .comparing(ProductOffer::getInStock).reversed()
                .thenComparing(ProductOffer::getPrice)
                .thenComparing(ProductOffer::getShopName));

        List<Review> reviews = externalApiResponses.stream()
                .map(ExternalApiResponse::reviews)
                .flatMap(List::stream)
                .map(reviewResponse -> new Review(
                        reviewResponse.username(),
                        reviewResponse.comment()
                ))
                .toList();

        String description = externalApiResponses.stream()
                .map(ExternalApiResponse::description)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Description unavailable");

        return new Product(
                UUID.randomUUID().toString(),
                productName,
                description,
                productOffers,
                reviews
        );
    }
}
