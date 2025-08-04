package com.example.ErrorHandlingAndExceptionManagement.contorller;

import com.example.ErrorHandlingAndExceptionManagement.entity.Product;
import com.example.ErrorHandlingAndExceptionManagement.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id, @RequestBody String password) throws AccessDeniedException {
        productService.deleteById(id, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
