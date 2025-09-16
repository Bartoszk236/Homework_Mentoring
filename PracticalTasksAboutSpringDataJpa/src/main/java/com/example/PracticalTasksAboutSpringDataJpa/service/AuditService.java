package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.AuditLog;
import com.example.PracticalTasksAboutSpringDataJpa.entity.Order;
import com.example.PracticalTasksAboutSpringDataJpa.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditService {
    private final AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void createAuditLog(Order order, String description) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityClass(order.getClass().toString());
        auditLog.setEntityId(order.getId());
        auditLog.setDescription(description);
        auditLogRepository.save(auditLog);
    }
}
