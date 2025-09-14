package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.service;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto.OrderItemDto;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Customer;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Order;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.OrderItem;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.CustomerRepository;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.OrderRepository;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.model.OrderStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public Order createOrder(Long customerId, List<OrderItemDto> items) {
        try {
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new IllegalArgumentException("Customer with id: " + customerId + " not found")
            );

            Order order = new Order();

            items.forEach(item -> {
                Product product = productRepository.findById(item.productId()).orElseThrow(
                        () -> new IllegalArgumentException("Product with id: " + item.productId() + " not found")
                );

                Integer orderItemQuantity = item.quantity();
                Integer productStock = product.getStockQuantity();

                if (productStock < orderItemQuantity) {
                    throw new IllegalArgumentException("Product order quantity is higher than stock quantity");
                }

                product.setStockQuantity(product.getStockQuantity() - orderItemQuantity);

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(orderItemQuantity);
                orderItem.setOrder(order);
            });

            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(NEW);
            order.setCustomer(customer);

            BigDecimal totalValue = order.getItems()
                    .stream()
                    .map(item -> item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            order.setTotalAmount(totalValue);

            return orderRepository.save(order);
        } catch (OptimisticLockException | ObjectOptimisticLockingFailureException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("Order with id: " + orderId + " not found")
        );

        if (order.getStatus() != SHIPPED) {
            order.getItems().forEach(item -> {
                Product product = item.getProduct();
                Integer quantityToIncrease = item.getQuantity();
                product.setStockQuantity(product.getStockQuantity() + quantityToIncrease);
            });

            order.setStatus(CANCELLED);
            orderRepository.saveAndFlush(order);
        } else {
            throw new IllegalStateException("you can't update order when order have status shipped");
        }
    }
}
