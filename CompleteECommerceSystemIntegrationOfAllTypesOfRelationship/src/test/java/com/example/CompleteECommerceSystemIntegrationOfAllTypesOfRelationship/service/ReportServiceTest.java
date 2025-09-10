package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.service;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.EntityInitialization;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto.CustomerReportDto;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto.InventoryReportDto;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Customer;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({ReportService.class, EntityInitialization.class})
class ReportServiceTest {
    @Autowired
    private ReportService service;
    @Autowired
    private EntityInitialization entityInitialization;
    private EntityInitialization.SeededData seededData;

    @BeforeEach
    void setUp() {
        seededData = entityInitialization.seedFull();
    }

    @Test
    void givenExampleDataWhenGenerateCustomerReportThenReturnCustomerReport() {
        //given
        Customer testCustomer = seededData.customers().getFirst();
        assertEquals("Alice", testCustomer.getProfile().getFirstName());

        BigDecimal expectedTotalValue = new BigDecimal("4129.88");
        Integer expectedOrderCounts = 1;
        String expectedFavouriteCategoryName = "Electronics";
        LocalDateTime expectedLastActivity = testCustomer.getOrders().getLast().getOrderDate();

        //when
        CustomerReportDto result = service.generateCustomerReport(testCustomer.getId());

        //then
        assertEquals(expectedTotalValue, result.totalOrdersValue());
        assertEquals(expectedOrderCounts, result.ordersCount());
        assertEquals(expectedFavouriteCategoryName, result.favouriteCategory().getName());
        assertEquals(expectedLastActivity, result.lastActivity());
    }

    @Test
    void givenExampleDataWhenGenerateInventoryReportThenReturnInventoryReport() {
        //given
        Product expectedLowStocksProduct = seededData.products()
                .stream()
                .filter(product -> product.getName().equals("Laptop Pro 14"))
                .findFirst()
                .orElseThrow();

        Product expectedMostFrequentlyPurchasedProduct = seededData.products()
                .stream()
                .filter(product -> product.getName().equals("Smartphone X"))
                .findFirst()
                .orElseThrow();

        BigDecimal expectedTotalValue = new BigDecimal("235959.50");

        //when
        InventoryReportDto result = service.generateInventoryReport();

        //then
        assertEquals(1, result.lowStocksProducts().size());
        assertTrue(result.lowStocksProducts().contains(expectedLowStocksProduct));
        assertEquals(expectedMostFrequentlyPurchasedProduct.getName(), result.mostFrequentlyPurchasedProduct().getName());
        assertEquals(expectedTotalValue, result.totalStockValue());
    }
}
