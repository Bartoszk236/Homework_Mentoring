package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.*;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityInitialization {
    private final TestEntityManager em;

    @Transactional
    public SeededData seedFull() {
        Category electronics = new Category()
                .setName("Electronics");
        em.persist(electronics);

        Category books = new Category()
                .setName("Books");
        em.persist(books);

        Tag tagNew = new Tag()
                .setName("New");
        em.persist(tagNew);

        Tag tagSale = new Tag()
                .setName("Sale");
        em.persist(tagSale);

        Product phone = new Product()
                .setName("Smartphone X")
                .setSku("PH-001")
                .setPrice(new BigDecimal("1999.99"))
                .setStockQuantity(50)
                .setCategory(electronics);
        em.persist(phone);

        Product laptop = new Product()
                .setName("Laptop Pro 14")
                .setSku("LP-014")
                .setPrice(new BigDecimal("5499.00"))
                .setStockQuantity(20)
                .setCategory(electronics);
        em.persist(laptop);

        Product book = new Product()
                .setName("Clean Architecture")
                .setSku("BK-777")
                .setPrice(new BigDecimal("129.90"))
                .setStockQuantity(200)
                .setCategory(books);
        em.persist(book);

        phone.addTagToProduct(tagNew, "tester");
        laptop.addTagToProduct(tagSale, "tester");
        book.addTagToProduct(tagNew, "tester");
        book.addTagToProduct(tagSale, "tester");

        Customer alice = new Customer()
                .setEmail("alice@example.com");
        em.persist(alice);

        CustomerProfile aliceProfile = new CustomerProfile();
        aliceProfile.setFirstName("Alice");
        aliceProfile.setLastName("Anderson");
        alice.setProfile(aliceProfile);
        em.persist(aliceProfile);

        Customer bob = new Customer()
                .setEmail("bob@example.com");
        em.persist(bob);

        CustomerProfile bobProfile = new CustomerProfile()
                .setFirstName("Bob")
                .setLastName("Brown");
        bob.setProfile(bobProfile);
        em.persist(bobProfile);

        Order order1 = new Order()
                .setOrderNumber("ORD-1001")
                .setOrderDate(LocalDateTime.now().minusDays(2))
                .setStatus(OrderStatus.NEW)
                .setCustomer(alice);
        em.persist(order1);

        OrderItem oi1 = new OrderItem()
                .setProduct(phone)
                .setQuantity(2)
                .setOrder(order1);
        em.persist(oi1);

        OrderItem oi2 = new OrderItem()
                .setProduct(book)
                .setQuantity(1)
                .setOrder(order1);
        em.persist(oi2);

        order1.recalculateTotal();

        Order order2 = new Order()
                .setOrderNumber("ORD-1002")
                .setOrderDate(LocalDateTime.now().minusDays(1))
                .setStatus(OrderStatus.COMPLETED)
                .setCustomer(bob);
        em.persist(order2);

        OrderItem oi3 = new OrderItem()
                .setProduct(laptop)
                .setQuantity(1);
        oi3.setOrder(order2);
        em.persist(oi3);

        order2.recalculateTotal();

        em.flush();
        em.clear();

        return new SeededData(
                Arrays.asList(electronics, books),
                Arrays.asList(phone, laptop, book),
                Arrays.asList(tagNew, tagSale),
                Arrays.asList(alice, bob),
                Arrays.asList(order1, order2)
        );
    }

    public record SeededData(
            List<Category> categories,
            List<Product> products,
            List<Tag> tags,
            List<Customer> customers,
            List<Order> orders) {
    }
}
