package com.example.OrderSystemBusinessLayer.service;

import com.example.OrderSystemBusinessLayer.client.PaymentGateway;
import com.example.OrderSystemBusinessLayer.dto.OrderRequest;
import com.example.OrderSystemBusinessLayer.dto.OrderResponse;
import com.example.OrderSystemBusinessLayer.entity.*;
import com.example.OrderSystemBusinessLayer.repository.CustomerRepository;
import com.example.OrderSystemBusinessLayer.repository.DiscountCodeRepository;
import com.example.OrderSystemBusinessLayer.repository.OrderRepository;
import com.example.OrderSystemBusinessLayer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.OrderSystemBusinessLayer.model.EmailMessageType.*;
import static com.example.OrderSystemBusinessLayer.model.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final BigDecimal MINIMUM_ORDER_VALUE_TO_FREE_SHIP = BigDecimal.valueOf(1000);
    private static final BigDecimal SHIPPING_COST = BigDecimal.valueOf(15);
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final EmailService emailService;
    private final PaymentGateway paymentGateway;
    private final CustomerRepository customerRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();

        Long customerId = orderRequest.customerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer with id: " + customerId + " not found"));
        order.setCustomer(customer);

        orderRequest.orderItemRequest().forEach(requestOrderItem -> {
            Long productId = requestOrderItem.productId();
            Integer requestOrderItemQuantity = requestOrderItem.quantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product with id: " + productId + " not found"));

            Integer productStockQuantity = product.getStockQuantity();
            if (productStockQuantity < requestOrderItemQuantity)
                throw new RuntimeException("Quantity in order cannot be more than stock quantity");

            product.setStockQuantity(productStockQuantity - requestOrderItemQuantity);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(requestOrderItemQuantity);

            order.addOrderItem(orderItem);
        });

        Set<OrderItem> orderItems = order.getOrderItems();

        BigDecimal totalValue = orderItems
                .stream()
                .map(orderItem -> {
                    BigDecimal productPrice = orderItem.getProduct().getPrice();
                    Integer orderItemQuantity = orderItem.getQuantity();

                    return productPrice.multiply(BigDecimal.valueOf(orderItemQuantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalValueAfterDiscount = totalValue;
        if (orderRequest.discountCodes() != null) {
            List<DiscountCode> validDiscountCodes = orderRequest.discountCodes()
                    .stream()
                    .map(code -> {
                        DiscountCode discountCode = discountCodeRepository.findByCode(code)
                                .orElseThrow(() -> new RuntimeException("Discount code: " + code + " not found"));

                        if (discountCode.getValidUntil().isBefore(LocalDate.now())) {
                            throw new RuntimeException("Discount code: " + code + " is expired");
                        }

                        BigDecimal minimumOrderValue = discountCode.getMinimumOrderValue();
                        if (totalValue.compareTo(minimumOrderValue) < 0)
                            throw new RuntimeException("Minimum Order Value is less than minimum (" +
                                    minimumOrderValue + ") to apply code");

                        return discountCode;
                    })
                    .toList();


            for (DiscountCode discountCode : validDiscountCodes) {
                BigDecimal percent = discountCode.getValue().divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
                BigDecimal valueToDecrease = totalValueAfterDiscount.multiply(percent);
                totalValueAfterDiscount = totalValueAfterDiscount.subtract(valueToDecrease);
            }
        }

        if (totalValueAfterDiscount.compareTo(MINIMUM_ORDER_VALUE_TO_FREE_SHIP) >= 0) {
            order.setShippingCost(BigDecimal.ZERO);
        } else {
            order.setShippingCost(SHIPPING_COST);
        }
        order.setTotalValue(totalValueAfterDiscount);

        int response = paymentGateway.initPayment(order.getIdentifyUuid(), totalValueAfterDiscount);
        if (response != 200) {
            emailService.sendEmail(customer.getEmail(), PAYMENT_FAILED);
            throw new RuntimeException("Payment failed with response code: " + response);
        }
        emailService.sendEmail(customer.getEmail(), PAYMENT_SUCCESS);

        order.setOrderStatus(PENDING);
        orderRepository.save(order);
        emailService.sendEmail(customer.getEmail(), ORDER_SUCCESS);
        return new OrderResponse(order.getId(), order.getTotalValue());
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order with id: " + orderId + " not found"));

        if (order.getOrderStatus() != PENDING)
            throw new RuntimeException("You cannot cancel order who have different status than pending");

        order.setOrderStatus(CANCELLED);
        Set<OrderItem> orderItems = order.getOrderItems();
        List<Product> productsToSave = new ArrayList<>();

        orderItems.forEach(orderItem -> {
            Product product = orderItem.getProduct();
            Integer quantityToIncrease = orderItem.getQuantity();

            product.setStockQuantity(product.getStockQuantity() + quantityToIncrease);
            productsToSave.add(product);
        });
        productRepository.saveAll(productsToSave);
        emailService.sendEmail(order.getCustomer().getEmail(), ORDER_CANCELED);
        orderRepository.save(order);
    }
}
