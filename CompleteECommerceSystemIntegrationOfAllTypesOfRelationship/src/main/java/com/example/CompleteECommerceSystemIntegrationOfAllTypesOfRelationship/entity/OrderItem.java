package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem setProduct(Product product) {
        this.product = product;
        product.addOrderItem(this);
        return this;
    }

    public OrderItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItem setOrder(Order order) {
        this.order = order;
        order.addOrderItems(this);
        return this;
    }
}
