package com.example.OrderSystemBusinessLayer.entity;

import com.example.OrderSystemBusinessLayer.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_status")
    @Setter
    private OrderStatus orderStatus;

    @Column(name = "shipping_cost")
    @Setter
    private BigDecimal shippingCost;

    @Column(name = "total_value")
    @Setter
    private BigDecimal totalValue;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "identify_uuid", updatable = false, unique = true, nullable = false)
    private String identifyUuid = UUID.randomUUID().toString();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(identifyUuid, order.identifyUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyUuid);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.addOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
