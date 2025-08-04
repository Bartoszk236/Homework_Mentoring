package com.example.JsonDeserializationWithCustomLogic.dto;

import com.example.JsonDeserializationWithCustomLogic.adnotation.TotalAmount;
import com.example.JsonDeserializationWithCustomLogic.deserializer.DeliveryDateDeserializer;
import com.example.JsonDeserializationWithCustomLogic.deserializer.DiscountDeserializer;
import com.example.JsonDeserializationWithCustomLogic.deserializer.OrderItemDeserializer;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@TotalAmount
public class OrderRequest {
    @JsonDeserialize(using = OrderItemDeserializer.class)
    private List<OrderItem> items; // Custom deserializer
    @JsonDeserialize(using = DeliveryDateDeserializer.class)
    private LocalDate deliveryDate; // Multiple formats
    @JsonDeserialize(using = DiscountDeserializer.class)
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal discount = BigDecimal.ZERO; // Percent or amount
    @JsonSetter(nulls = Nulls.SKIP)
    private String currency = "PLN"; // Default PLN
    private BigDecimal totalAmount;
}
