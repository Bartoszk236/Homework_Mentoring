package com.example.WorkingWithHeadersAndContentNegotiation.mapper;

import com.example.WorkingWithHeadersAndContentNegotiation.entity.Users;
import com.example.WorkingWithHeadersAndContentNegotiation.dto.PublicResponse;
import com.example.WorkingWithHeadersAndContentNegotiation.dto.SimpleResponse;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Object toPublicResponse(Users user) {
        return new PublicResponse(user.getFirstName(), user.getLastName());
    }

    public Object simpleResponse(Users user) {
        return new SimpleResponse(user.getFirstName(), user.getLastName(), user.getBirthDate());
    }
}
