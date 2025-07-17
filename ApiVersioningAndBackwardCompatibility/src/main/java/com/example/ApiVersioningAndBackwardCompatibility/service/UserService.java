package com.example.ApiVersioningAndBackwardCompatibility.service;

import com.example.ApiVersioningAndBackwardCompatibility.dto.UserRequest;
import com.example.ApiVersioningAndBackwardCompatibility.entity.Users;
import com.example.ApiVersioningAndBackwardCompatibility.mapper.UserMapper;
import com.example.ApiVersioningAndBackwardCompatibility.model.ApiVersion;
import com.example.ApiVersioningAndBackwardCompatibility.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public Object getUsers(ApiVersion apiVersion) {
        return switch (apiVersion) {
            case V1 -> userRepository.findAll().stream().map(mapper::toUserResponse).toList();
            case V2 -> userRepository.findAll().stream().map(mapper::toUserResponseV2).toList();
            case V3 -> userRepository.findAll().stream().map(mapper::toUserResponseV3).toList();
        };
    }

    public Object getUser(ApiVersion apiVersion, Long id) {
        return switch (apiVersion) {
            case V1 -> mapper.toUserResponse(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
            case V2 -> mapper.toUserResponseV2(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
            case V3 -> mapper.toUserResponseV3(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        };
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(ApiVersion apiVersion, Long id, UserRequest userRequest) {
        userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Users newUser = mapper.toUserEntity(userRequest, id);
        userRepository.save(newUser);
    }

    public void createUser(UserRequest userRequest) {
        Users users = mapper.toUserEntity(userRequest, null);
        userRepository.save(users);
    }
}
