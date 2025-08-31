package com.example.LoggingWithLevelsJsonLogsAndMdc.service;

import com.example.LoggingWithLevelsJsonLogsAndMdc.entity.User;
import com.example.LoggingWithLevelsJsonLogsAndMdc.exception.UserNotFoundException;
import com.example.LoggingWithLevelsJsonLogsAndMdc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) {
        log.info("Getting user with id: {}", id);
        return userRepository.findById(id).orElseThrow(
                () -> {
                    log.debug("repository returned empty user with id: {}", id);
                    return new UserNotFoundException("Not found any user with id: " + id);
                });
    }
}
