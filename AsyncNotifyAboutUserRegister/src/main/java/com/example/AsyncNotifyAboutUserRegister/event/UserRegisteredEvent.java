package com.example.AsyncNotifyAboutUserRegister.event;

public record UserRegisteredEvent(
        String name, String email
) {
}
