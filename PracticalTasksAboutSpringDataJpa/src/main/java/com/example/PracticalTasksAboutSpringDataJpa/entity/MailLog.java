package com.example.PracticalTasksAboutSpringDataJpa.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "mail_logs")
public class MailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_log_id")
    private Long id;

    @Setter
    @Column(name = "sent_to")
    private String sentTo;

    @Setter
    @Column(name = "description")
    private String description;
}
