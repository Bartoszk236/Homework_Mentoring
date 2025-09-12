package com.example.AsyncNotifyAboutUserRegister.service;

import com.example.AsyncNotifyAboutUserRegister.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WelcomeEmailService {

    @Async
    @EventListener
    public void handleUserRegisterEvent(UserRegisteredEvent event) {
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("Sending new email notification to: " + event.email());
            Thread.sleep(1500);
            long endTime = System.currentTimeMillis();
            log.info(
                    "Thread: {} | Duration: {} ms",
                    Thread.currentThread().getName(),
                    endTime - startTime
            );
        } catch (InterruptedException exception) {
            log.info("Thread: {} | Interrupted", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
    }
}
