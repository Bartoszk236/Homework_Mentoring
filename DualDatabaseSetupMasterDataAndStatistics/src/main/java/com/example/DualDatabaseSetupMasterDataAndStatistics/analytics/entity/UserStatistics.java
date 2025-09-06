package com.example.DualDatabaseSetupMasterDataAndStatistics.analytics.entity;

import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_statistics")
@Setter
public class UserStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistics_id")
    private Long id;
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
