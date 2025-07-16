package com.ecommerce.api.mapper;

import com.ecommerce.api.dto.request.RegisterRequestDTO;
import com.ecommerce.api.persistence.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    public static User dtoToEntity(RegisterRequestDTO registerRequestDTO, PasswordEncoder passwordEncoder) {
        User user = new User();

        user.setName(registerRequestDTO.getName());
        user.setSurname(registerRequestDTO.getSurname());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        return user;
    }
}