package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.service;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto.OrderItemDto;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Customer;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Order;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.OrderItem;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.model.OrderStatus;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.CustomerRepository;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.OrderRepository;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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

                new OrderItem()
                        .setProduct(product)
                        .setQuantity(orderItemQuantity)
                        .setOrder(order);
            });

            order.setOrderNumber(UUID.randomUUID().toString())
                    .setOrderDate(LocalDateTime.now())
                    .setStatus(OrderStatus.NEW)
                    .setCustomer(customer);

            order.recalculateTotal();
            return orderRepository.save(order);
        } catch (OptimisticLockException | ObjectOptimisticLockingFailureException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("Order with id: " + orderId + " not found")
        );

        if (order.updateIsPossible()) {
            order.getItems().forEach(item -> {
                Product product = item.getProduct();
                Integer quantityToIncrease = item.getQuantity();
                product.setStockQuantity(product.getStockQuantity() + quantityToIncrease);
            });

            order.setStatus(OrderStatus.CANCELLED);
        }
    }
}
