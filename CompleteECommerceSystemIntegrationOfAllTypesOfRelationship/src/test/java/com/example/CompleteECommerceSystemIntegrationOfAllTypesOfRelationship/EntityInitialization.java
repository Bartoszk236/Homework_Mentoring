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
        Category electronics = new Category();
        electronics.setName("Electronics");
        em.persist(electronics);

        Category books = new Category();
        books.setName("Books");
        em.persist(books);

        Tag tagNew = new Tag();
        tagNew.setName("New");
        em.persist(tagNew);

        Tag tagSale = new Tag();
        tagSale.setName("Sale");
        em.persist(tagSale);

        Product phone = new Product();
        phone.setName("Smartphone X");
        phone.setSku("PH-001");
        phone.setPrice(new BigDecimal("1999.99"));
        phone.setStockQuantity(50);
        phone.setCategory(electronics);
        em.persist(phone);

        Product laptop = new Product();
        laptop.setName("Laptop Pro 14");
        laptop.setSku("LP-014");
        laptop.setPrice(new BigDecimal("5499.00"));
        laptop.setStockQuantity(20);
        laptop.setCategory(electronics);
        em.persist(laptop);

        Product book = new Product();
        book.setName("Clean Architecture");
        book.setSku("BK-777");
        book.setPrice(new BigDecimal("129.90"));
        book.setStockQuantity(200);
        book.setCategory(books);
        em.persist(book);

        phone.addTagToProduct(tagNew, "tester");
        laptop.addTagToProduct(tagSale, "tester");
        book.addTagToProduct(tagNew, "tester");
        book.addTagToProduct(tagSale, "tester");

        Customer alice = new Customer();
        alice.setEmail("alice@example.com");
        em.persist(alice);

        CustomerProfile aliceProfile = new CustomerProfile();
        aliceProfile.setFirstName("Alice");
        aliceProfile.setLastName("Anderson");
        alice.setProfile(aliceProfile);
        em.persist(aliceProfile);

        Customer bob = new Customer();
        bob.setEmail("bob@example.com");
        em.persist(bob);

        CustomerProfile bobProfile = new CustomerProfile();
        bobProfile.setFirstName("Bob");
        bobProfile.setLastName("Brown");
        bob.setProfile(bobProfile);
        em.persist(bobProfile);

        Order order1 = new Order();
        order1.setOrderNumber("ORD-1001");
        order1.setOrderDate(LocalDateTime.now().minusDays(2));
        order1.setStatus(OrderStatus.NEW);
        order1.setCustomer(alice);
        order1.setTotalAmount(new BigDecimal("4129.88"));
        em.persist(order1);

        OrderItem oi1 = new OrderItem();
        oi1.setProduct(phone);
        oi1.setQuantity(2);
        oi1.setOrder(order1);
        em.persist(oi1);

        OrderItem oi2 = new OrderItem();
        oi2.setProduct(book);
        oi2.setQuantity(1);
        oi2.setOrder(order1);
        em.persist(oi2);

        Order order2 = new Order();
        order2.setOrderNumber("ORD-1002");
        order2.setOrderDate(LocalDateTime.now().minusDays(1));
        order2.setStatus(OrderStatus.COMPLETED);
        order2.setCustomer(bob);
        order2.setTotalAmount(new BigDecimal("5499.00"));
        em.persist(order2);

        OrderItem oi3 = new OrderItem();
        oi3.setProduct(laptop);
        oi3.setQuantity(1);
        oi3.setOrder(order2);
        em.persist(oi3);

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
