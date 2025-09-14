package com.example.UserSystemWithProfiles.service;

import com.example.UserSystemWithProfiles.entity.User;
import com.example.UserSystemWithProfiles.entity.UserProfile;
import com.example.UserSystemWithProfiles.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void createUserWithProfile(String email, String password, String firstName, String lastName) {
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("Email already taken");

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.linkUserProfile(userProfile);

        userRepository.save(user);
        log.info("User with email {} has been created", email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User with email " + email + " not found")
        );
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
        log.info("User with id {} has been deleted", userId);
    }
}
