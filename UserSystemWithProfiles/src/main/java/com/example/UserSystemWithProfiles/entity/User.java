package com.example.UserSystemWithProfiles.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    public User linkUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        userProfile.setUser(this);
        return this;
    }

    public User unlinkUserProfile(UserProfile userProfile) {
        this.userProfile = null;
        userProfile.setUser(null);
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
