package com.example.ECommerceProductAggregator.controller;

import com.example.ECommerceProductAggregator.dto.Product;
import com.example.ECommerceProductAggregator.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("/best-offers")
    public ResponseEntity<Product> findBestOffers(
            @RequestParam("productName") String productName
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(offerService.findBestOffers(productName));
    }
}
