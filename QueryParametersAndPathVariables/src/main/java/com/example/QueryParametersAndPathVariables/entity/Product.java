package com.example.QueryParametersAndPathVariables.entity;

import com.example.QueryParametersAndPathVariables.model.Category;
import com.example.QueryParametersAndPathVariables.model.Subcategory;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Subcategory subcategory;
    private String tag;
    @CreationTimestamp
    private LocalDateTime createdOn;
}
