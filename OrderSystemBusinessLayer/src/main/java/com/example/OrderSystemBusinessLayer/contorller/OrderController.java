package com.example.OrderSystemBusinessLayer.contorller;

import com.example.OrderSystemBusinessLayer.dto.OrderRequest;
import com.example.OrderSystemBusinessLayer.dto.OrderResponse;
import com.example.OrderSystemBusinessLayer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(orderRequest));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
