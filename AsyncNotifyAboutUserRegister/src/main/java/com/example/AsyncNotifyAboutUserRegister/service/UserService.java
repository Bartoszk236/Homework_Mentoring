package com.example.AsyncNotifyAboutUserRegister.service;

import com.example.AsyncNotifyAboutUserRegister.event.UserRegisteredEvent;
import com.example.AsyncNotifyAboutUserRegister.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ApplicationEventPublisher eventPublisher;
    private final List<User> users = new ArrayList<>();

    public void registerUser(User user) {
        users.add(user);
        eventPublisher.publishEvent(new UserRegisteredEvent(user.name(), user.email()));
    }
}
