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
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToOne(mappedBy = "userProfile")
    private User user;

    public UserProfile setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserProfile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserProfile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserProfile setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    void setUser(User user) {
        this.user = user;
    }
}
