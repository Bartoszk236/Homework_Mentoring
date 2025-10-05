package com.example.OrderSystemBusinessLayer.service;

import com.example.OrderSystemBusinessLayer.model.EmailMessageType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Async
    public void sendEmail(String to, EmailMessageType action) {
        switch (action) {
            case PAYMENT_SUCCESS -> sendPaymentSuccess(to);
            case PAYMENT_FAILED -> sendPaymentFailed(to);
            case ORDER_SUCCESS -> sendOrderSuccess(to);
            case ORDER_CANCELED -> sendOrderCancelled(to);
        }
    }

    private void sendOrderCancelled(String to) {
        System.out.println("SENDING EMAIL");
        System.out.println("TO: " + to);
        System.out.println("Subject: ORDER CANCELLED");
    }

    private void sendOrderSuccess(String to) {
        System.out.println("SENDING EMAIL");
        System.out.println("TO: " + to);
        System.out.println("Subject: ORDER SUCCESS");
    }

    private void sendPaymentFailed(String to) {
        System.out.println("SENDING EMAIL");
        System.out.println("TO: " + to);
        System.out.println("Subject: PAYMENT FAILED");
    }

    private void sendPaymentSuccess(String to) {
        System.out.println("SENDING EMAIL");
        System.out.println("TO: " + to);
        System.out.println("Subject: PAYMENT SUCCESS");
    }
}
