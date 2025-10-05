package com.example.OrderSystemBusinessLayer;

import com.example.OrderSystemBusinessLayer.entity.*;
import com.example.OrderSystemBusinessLayer.model.OrderStatus;
import com.example.OrderSystemBusinessLayer.repository.*;
import com.github.javafaker.Faker;
import org.hibernate.AssertionFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SampleDataBuilder {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository  customerRepository;
    @Autowired
    private DiscountCodeRepository discountCodeRepository;
    @Autowired
    private OrderRepository orderRepository;
    private final Faker faker = new Faker();

    public void initData() {
        List<Product> productsToSave = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            productsToSave.add(createProduct());
        }
        productRepository.saveAll(productsToSave);

        List<DiscountCode>  discountCodesToSave = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            discountCodesToSave.add(createDiscountCode());
        }
        discountCodeRepository.saveAll(discountCodesToSave);

        List<Customer> customersToSave = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customersToSave.add(createCustomer());
        }
        customerRepository.saveAll(customersToSave);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setName(faker.name().name());
        product.setStockQuantity(faker.number().numberBetween(1, 10));
        product.setPrice(new BigDecimal(faker.number().numberBetween(1, 100)));
        return product;
    }

    private DiscountCode createDiscountCode() {
        DiscountCode discountCode = new DiscountCode();
        discountCode.setCode(faker.regexify("[a-zA-Z0-9]{18}"));
        discountCode.setValidUntil(LocalDate.now().plusDays(faker.number().numberBetween(1, 365)));
        discountCode.setValue(new BigDecimal(faker.number().numberBetween(1, 50)));
        discountCode.setMinimumOrderValue(new BigDecimal(faker.number().numberBetween(1, 500)));
        return discountCode;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(faker.name().firstName());
        customer.setLastName(faker.name().lastName());
        customer.setEmail(faker.internet().emailAddress());
        return customer;
    }

    @Transactional
    public Order createOrder(OrderStatus orderStatus, Long productId, Long customerId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AssertionFailure("Product with id " + productId + " does not exist"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AssertionFailure("Customer with id " + customerId + " does not exist"));

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);

        Order order = new Order();
        order.setCustomer(customer);
        order.addOrderItem(orderItem);
        order.setOrderStatus(orderStatus);
        return orderRepository.saveAndFlush(order);
    }
}
