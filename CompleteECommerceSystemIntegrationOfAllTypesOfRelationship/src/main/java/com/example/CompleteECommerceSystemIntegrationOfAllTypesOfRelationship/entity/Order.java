package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_amount", precision = 6, scale = 2)
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void recalculateTotal() {
        this.totalAmount = items.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public Order setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Order setCustomer(Customer customer) {
        this.customer = customer;
        customer.addOrder(this);
        return this;
    }

    public boolean updateIsPossible() {
        if (this.status == OrderStatus.SHIPPED)
            throw new IllegalStateException("you can't update order when order have status shipped");
        return true;
    }

    void addOrderItems(OrderItem item) {
        this.items.add(item);
    }
}
