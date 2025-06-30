package com.example.ErrorHandlingAndExceptionManagement.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ErrorInfo {
    AtomicInteger counter = new AtomicInteger();
    long lastUpdateTime = System.currentTimeMillis();
}
