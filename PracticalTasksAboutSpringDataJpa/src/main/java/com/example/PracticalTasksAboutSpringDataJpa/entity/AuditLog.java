package com.example.PracticalTasksAboutSpringDataJpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_log_id")
    private Long id;

    @Setter
    @Column(name = "entity_class")
    private String entityClass;

    @Setter
    @Column(name = "entity_id")
    private Long entityId;

    @Setter
    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
