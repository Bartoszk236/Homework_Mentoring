package com.example.PracticalTasksAboutSpringDataJpa.entity;

import com.example.PracticalTasksAboutSpringDataJpa.model.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "products")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Setter
    @Column(name = "price")
    private BigDecimal price;

    @Setter
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && category == product.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category);
    }
}
