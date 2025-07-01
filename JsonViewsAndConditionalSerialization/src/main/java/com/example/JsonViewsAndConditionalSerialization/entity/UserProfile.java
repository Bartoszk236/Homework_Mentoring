package com.example.JsonViewsAndConditionalSerialization.entity;

import com.example.JsonViewsAndConditionalSerialization.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Admin.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String firstName;
    @JsonView(Views.Public.class)
    private String email;
    @JsonView(Views.Owner.class)
    private String phone; // Only owner/admin
    @JsonView(Views.Admin.class)
    private String ssn; // Only admin
    @JsonView(Views.Owner.class)
    private LocalDateTime lastLogin; // Only owner/admin
    @JsonView(Views.Admin.class)
    private LocalDateTime createdAt;
    @JsonView(Views.Admin.class)
    private LocalDateTime updatedAt;
    @JsonView(Views.Admin.class)
    private boolean active;
}
