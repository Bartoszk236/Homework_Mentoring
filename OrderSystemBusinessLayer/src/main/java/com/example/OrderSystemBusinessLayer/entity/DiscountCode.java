package com.example.OrderSystemBusinessLayer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "discount_codes")
@Getter
@Setter
public class DiscountCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dicount_code_id")
    @Setter(NONE)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @Column(name = "value_percent", nullable = false)
    private BigDecimal value;

    @Column(name = "minimum_order_value", nullable = false)
    private BigDecimal minimumOrderValue;
}
