package com.example.ErrorHandlingAndExceptionManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 3, max = 20, message = "{validation.product.name}")
    private String productName;
    @NotNull(message = "{validation.product.description}")
    private String productDescription;
    @Positive(message = "{validation.product.price}")
    private BigDecimal productPrice;
    @Positive(message = "{validation.product.quantity}")
    private int productQuantity;
}
