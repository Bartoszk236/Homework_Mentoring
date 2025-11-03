package com.example.OrderSystemBusinessLayer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "quantity")
    @Setter
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id")
    @Setter(AccessLevel.MODULE)
    private Order order;

    @Column(name = "identify_uuid", updatable = false, unique = true, nullable = false)
    private String identifyUuid = UUID.randomUUID().toString();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(identifyUuid, orderItem.identifyUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUuid);
    }

    public void setProduct(Product product) {
        this.product = product;
        product.addOrderItem(this);
    }
}
