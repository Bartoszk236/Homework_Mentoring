package com.example.UserSystemWithProfiles.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users_profiles")
@Getter
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Setter
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Setter
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "userProfile")
    private User user;

    void setUser(User user) {
        this.user = user;
    }
}
