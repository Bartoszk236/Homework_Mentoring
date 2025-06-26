package com.example.JsonDeserializationWithCustomLogic.validator;

import com.example.JsonDeserializationWithCustomLogic.dto.OrderItem;
import com.example.JsonDeserializationWithCustomLogic.dto.OrderRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderRequestBuilder {

    OrderRequest createOrderWithItemsNull() {
        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setItems(null);
        orderRequest.setDeliveryDate(LocalDate.of(2025, 7, 1));
        orderRequest.setDiscount(new BigDecimal("5.00"));
        orderRequest.setCurrency("PLN");
        orderRequest.setTotalAmount(new BigDecimal("35.00"));

        return orderRequest;
    }

    OrderRequest createOrderWithItemsEmptyList() {
        OrderRequest orderRequest = new OrderRequest();

        List<OrderItem> items = List.of(
        );

        orderRequest.setItems(items);
        orderRequest.setDeliveryDate(LocalDate.of(2025, 7, 1));
        orderRequest.setDiscount(new BigDecimal("5.00"));
        orderRequest.setCurrency("PLN");
        orderRequest.setTotalAmount(new BigDecimal("35.00"));

        return orderRequest;
    }

    OrderRequest createOrderWithDiscountNull() {
        OrderRequest orderRequest = new OrderRequest();

        List<OrderItem> items = List.of(
                new OrderItem(1L, 2, new BigDecimal("10.00")),
                new OrderItem(2L, 1, new BigDecimal("20.00"))
        );

        orderRequest.setItems(items);
        orderRequest.setDeliveryDate(LocalDate.of(2025, 7, 1));
        orderRequest.setDiscount(null);
        orderRequest.setCurrency("PLN");
        orderRequest.setTotalAmount(new BigDecimal("35.00"));

        return orderRequest;
    }

    OrderRequest createOrderWithTotalAmountNull() {
        OrderRequest orderRequest = new OrderRequest();

        List<OrderItem> items = List.of(
                new OrderItem(1L, 2, new BigDecimal("10.00")),
                new OrderItem(2L, 1, new BigDecimal("20.00"))
        );

        orderRequest.setItems(items);
        orderRequest.setDeliveryDate(LocalDate.of(2025, 7, 1));
        orderRequest.setDiscount(new BigDecimal("5.00"));
        orderRequest.setCurrency("PLN");
        orderRequest.setTotalAmount(null);

        return orderRequest;
    }

    OrderRequest createOrderRequestWithValidDataAndDiscountAmount() {
        OrderRequest orderRequest = new OrderRequest();

        List<OrderItem> items = List.of(
                new OrderItem(1L, 2, new BigDecimal("10.00")),
                new OrderItem(2L, 1, new BigDecimal("20.00"))
        );

        orderRequest.setItems(items);
        orderRequest.setDeliveryDate(LocalDate.of(2025, 7, 1));
        orderRequest.setDiscount(new BigDecimal("5.00"));
        orderRequest.setCurrency("PLN");
        orderRequest.setTotalAmount(new BigDecimal("35.00"));

        return orderRequest;
    }

    OrderRequest createOrderRequestWithValidDataAndDiscountPercent() {
        OrderRequest orderRequest = new OrderRequest();

        List<OrderItem> items = List.of(
                new OrderItem(1L, 2, new BigDecimal("10.00")),
                new OrderItem(2L, 1, new BigDecimal("20.00"))
        );

        orderRequest.setItems(items);
        orderRequest.setDeliveryDate(LocalDate.of(2025, 7, 1));
        orderRequest.setDiscount(new BigDecimal("0.10"));
        orderRequest.setCurrency("PLN");
        orderRequest.setTotalAmount(new BigDecimal("36.00"));

        return orderRequest;
    }

    OrderRequest createOrderWithMissMatchActualAndExceptedTotalAmount() {
        OrderRequest orderRequest = new OrderRequest();

        List<OrderItem> items = List.of(
                new OrderItem(1L, 2, new BigDecimal("10.00")),
                new OrderItem(2L, 1, new BigDecimal("20.00"))
        );

        orderRequest.setItems(items);
        orderRequest.setDeliveryDate(LocalDate.of(2025, 7, 1));
        orderRequest.setDiscount(new BigDecimal("0.10"));
        orderRequest.setCurrency("PLN");
        orderRequest.setTotalAmount(new BigDecimal("33.00"));

        return orderRequest;
    }
}
