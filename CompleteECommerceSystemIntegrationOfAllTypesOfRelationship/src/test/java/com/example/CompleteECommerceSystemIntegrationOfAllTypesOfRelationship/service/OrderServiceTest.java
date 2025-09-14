package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.service;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.EntityInitialization;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto.OrderItemDto;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Customer;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Order;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.OrderItem;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.model.OrderStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({OrderService.class, EntityInitialization.class})
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private EntityManager em;
    @Autowired
    private EntityInitialization entityInitialization;
    private EntityInitialization.SeededData seededData;

    @BeforeEach
    void setUp() {
        seededData = entityInitialization.seedFull();
    }

    @Test
    void givenValidDataWhenCreateOrderThenReturnCreatedOrder() {
        //expected
        OrderStatus expectedStatus = OrderStatus.NEW;
        BigDecimal expectedTotal = new BigDecimal("4389.68");

        //given
        Customer testCustomer = seededData.customers().getFirst();

        Product testProduct1 = seededData.products().getFirst();
        Product testProduct2 = seededData.products().getLast();

        OrderItemDto dto1 = new OrderItemDto(testProduct1.getId(), 2);
        OrderItemDto dto2 = new OrderItemDto(testProduct2.getId(), 3);

        //when
        Order call = orderService.createOrder(testCustomer.getId(), List.of(dto1, dto2));
        em.clear();

        //then
        Order result = em.find(Order.class, call.getId());

        //fields
        assertEquals(expectedStatus, result.getStatus());
        assertEquals(expectedTotal, result.getTotalAmount());

        //customer
        assertEquals(testCustomer.getEmail(), result.getCustomer().getEmail());

        //order items
        List<OrderItem> orderItemsResult = result.getItems();
        assertEquals(2, orderItemsResult.size());
        assertThat(orderItemsResult
                .stream()
                .map(OrderItem::getProduct)
                .toList())
                .containsExactlyInAnyOrder(testProduct1, testProduct2);

        //order
        assertEquals(result, orderItemsResult.getFirst().getOrder());
    }

    @Test
    void givenValidOrderWhenCancelOrderThenChangeStatusToCancelled() {
        //given
        OrderStatus expectedStatus = OrderStatus.CANCELLED;
        Order testOrder = seededData.orders().getFirst();
        OrderItem io = testOrder.getItems().getFirst();
        Product testProduct = io.getProduct();
        Integer oldStockQuantity = testProduct.getStockQuantity();
        Integer valueToIncrease = io.getQuantity();
        Integer expectedStockQuantity = oldStockQuantity + valueToIncrease;

        assertEquals(OrderStatus.NEW, testOrder.getStatus());

        //when
        orderService.cancelOrder(testOrder.getId());
        em.clear();

        //then
        Order result = em.find(Order.class, testOrder.getId());
        assertEquals(expectedStatus, result.getStatus());

        Product resultProduct = em.find(Product.class, testProduct.getId());
        assertEquals(expectedStockQuantity, resultProduct.getStockQuantity());
    }

    @Test
    void givenOrderWithStatusShippedWhenCancelOrderThenThrowExceptionAndStockQuantityNoChange() {
        //given
        Order order = seededData.orders().getFirst();
        order.setStatus(OrderStatus.SHIPPED);
        order = em.merge(order);
        em.flush();
        em.clear();

        Order testOrder = em.find(Order.class, order.getId());

        OrderItem io = testOrder.getItems().getFirst();

        Product testProduct = io.getProduct();
        Integer oldStockQuantity = testProduct.getStockQuantity();

        //when
        Exception exception = assertThrows(IllegalStateException.class,
                () -> orderService.cancelOrder(testOrder.getId()));
        em.clear();

        //then
        assertEquals("you can't update order when order have status shipped", exception.getMessage());

        assertEquals(OrderStatus.SHIPPED, testOrder.getStatus());
        Product resultProduct = em.find(Product.class, testProduct.getId());
        assertEquals(oldStockQuantity, resultProduct.getStockQuantity());
    }
}
