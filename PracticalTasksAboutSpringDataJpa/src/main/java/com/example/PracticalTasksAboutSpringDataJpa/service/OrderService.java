package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Customer;
import com.example.PracticalTasksAboutSpringDataJpa.entity.Order;
import com.example.PracticalTasksAboutSpringDataJpa.repository.CustomerRepository;
import com.example.PracticalTasksAboutSpringDataJpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuditService auditService;
    private final EmailSenderService emailSenderService;
    private final CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Order createOrderFirstVersion(Long customerId, String description, LocalDateTime executionTime) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("Customer with id: " + customerId + " not found")
        );

        Order order = new Order();
        order.setDescription(description);
        order.setExecutionTime(executionTime);
        order.setCustomer(customer);

        orderRepository.save(order);
        auditService.createAuditLog(order, "Created Order");
        emailSenderService.sentEmail(customer.getEmail());
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createOrderSecondVersion(Long customerId, String description, LocalDateTime executionTime) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("Customer with id: " + customerId + " not found")
        );

        Order order = new Order();
        order.setDescription(description);
        order.setExecutionTime(executionTime);
        order.setCustomer(customer);

        orderRepository.save(order);
        auditService.createAuditLog(order, "Created Order");
        emailSenderService.sentEmailSecondVersion(customer.getEmail());

        if (executionTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Execution time must be before execution time");
        return order;
    }
}
