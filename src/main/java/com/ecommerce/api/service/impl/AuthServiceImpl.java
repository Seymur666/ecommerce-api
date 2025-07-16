package com.ecommerce.api.service.impl;

import com.ecommerce.api.config.TokenManager;
import com.ecommerce.api.dto.request.LoginRequestDTO;
import com.ecommerce.api.dto.request.RegisterRequestDTO;
import com.ecommerce.api.dto.response.ResponseDTO;
import com.ecommerce.api.mapper.UserMapper;
import com.ecommerce.api.model.enums.RoleName;
import com.ecommerce.api.persistence.entity.User;
import com.ecommerce.api.persistence.entity.UserRole;
import com.ecommerce.api.persistence.repository.UserRepository;
import com.ecommerce.api.persistence.repository.UserRoleRepository;
import com.ecommerce.api.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;
    TokenManager tokenManager;

    @Override
    public ResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = UserMapper.dtoToEntity(registerRequestDTO, passwordEncoder);

        UserRole userRole = userRoleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User role not found"));

        user.getUserRoles().add(userRole);

        userRepository.save(user);

        return ResponseDTO.builder()
                .status(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .build();
    }

    @Override
    public ResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        String roles = user.getUserRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        Map<String, String> tokens = tokenManager.generateToken(user.getEmail(), roles);

        return ResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(tokens)
                .build();
    }
}
