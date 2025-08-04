package com.example.JsonDeserializationWithCustomLogic;

import com.example.JsonDeserializationWithCustomLogic.dto.OrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @PostMapping("/api/orders")
    public ResponseEntity<OrderRequest> postOrder(@RequestBody @Validated OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRequest);
    }
}
