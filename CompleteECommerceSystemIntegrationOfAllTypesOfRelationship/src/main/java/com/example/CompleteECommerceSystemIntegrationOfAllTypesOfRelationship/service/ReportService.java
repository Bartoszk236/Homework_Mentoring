package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.service;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto.CustomerReportDto;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto.InventoryReportDto;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Category;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.CustomerRepository;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CustomerReportDto generateCustomerReport(Long customerId) {
        if (!customerRepository.existsById(customerId))
            throw new IllegalArgumentException("Customer with id " + customerId + " does not exist");

        BigDecimal totalValue = customerRepository.findTotalValueOrdersByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("customer don't have any orders"));

        Integer countOfOrders = customerRepository.findCountOfOrdersByCustomerId(customerId);
        Category favoritesCategories = customerRepository.findFavoriteCategoryByCustomerId(customerId);
        LocalDateTime lastActivity = customerRepository.findLastOrderDateByCustomerId(customerId);

        return new CustomerReportDto(totalValue, countOfOrders, favoritesCategories, lastActivity);
    }

    public InventoryReportDto generateInventoryReport() {
        List<Product> productsWithLowStock = productRepository.findProductWithLowStocks(30);
        Product theBestProduct = productRepository.findTheBestSellingProduct();

        BigDecimal totalStockValue = productRepository.findAll()
                .stream()
                .map(product -> product.getPrice()
                        .multiply(BigDecimal.valueOf(product.getStockQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new InventoryReportDto(productsWithLowStock, theBestProduct, totalStockValue);
    }
}
