package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @Column(name = "order_number")
    private String orderNumber;

    @Setter
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Setter
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Setter
    @Column(name = "total_amount", precision = 6, scale = 2)
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.addOrder(this);
    }

    void addOrderItems(OrderItem item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "XXX" +
                "Order{" +
                "customer=" + customer +
                ", id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", items=" + items +
                '}';
    }
}
