package com.example.ChatViaWebSocketAndStomp.dto;

public record ChatMessage(
        String sender,
        String content,
        String type
) {
}
