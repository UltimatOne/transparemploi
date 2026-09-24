package com.transparemploi.backend.mapper;

import org.springframework.stereotype.Component;

import com.transparemploi.backend.dto.UserResponse;
import com.transparemploi.backend.model.User;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        return res;
    }
}
