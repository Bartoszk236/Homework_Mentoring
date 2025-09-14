package com.example.UserSystemWithProfiles.service;

import com.example.UserSystemWithProfiles.entity.User;

public interface UserService {
    void createUserWithProfile(String email, String password, String firstName, String lastName);

    User findByEmail(String email);

    void deleteUser(Long userId);
}
