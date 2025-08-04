package com.example.WorkingWithHeadersAndContentNegotiation.service;

import com.example.WorkingWithHeadersAndContentNegotiation.entity.Users;
import com.example.WorkingWithHeadersAndContentNegotiation.mapper.Mapper;
import com.example.WorkingWithHeadersAndContentNegotiation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public Object getUserById(Long id, String type, String apiVersion, String authorization, String ifNoneMatch) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (authorization == null || authorization.isBlank()) {
            return mapper.toPublicResponse(user);
        }
        if (type.equalsIgnoreCase("text/plain")) {
            return user.getEmail();
        }
        if (apiVersion.equals("1.0")) {
            return mapper.simpleResponse(user);
        }
        if (apiVersion.equals("2.0")) {
            return user;
        }
        throw new RuntimeException("Unsupported API version");
    }

    public String getUserEtag(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return "\"" + user.getUpdatedAt().toString().hashCode() + "\"";
    }
}
