package com.example.PracticalTasksAboutSpringDataJpa.contorller;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Product;
import com.example.PracticalTasksAboutSpringDataJpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDir") SortDirection sortDirection
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findProducts(sortBy, sortDirection));
    }
}
