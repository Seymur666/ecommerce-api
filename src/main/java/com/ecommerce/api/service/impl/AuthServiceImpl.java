package com.ecommerce.api.service.impl;

import com.ecommerce.api.security.jwt.TokenManager;
import com.ecommerce.api.dto.request.LoginRequestDTO;
import com.ecommerce.api.dto.request.RegisterRequestDTO;
import com.ecommerce.api.dto.response.LoginResponseDTO;
import com.ecommerce.api.dto.response.RegisterResponseDTO;
import com.ecommerce.api.mapper.UserMapper;
import com.ecommerce.api.model.BaseResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

import static com.ecommerce.api.model.constants.MessageConstants.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;
    TokenManager tokenManager;
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<BaseResponse<RegisterResponseDTO>> register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, USER_ALREADY_EXISTS);
        }

        User user = UserMapper.dtoToEntity(registerRequestDTO, passwordEncoder);

        UserRole userRole = userRoleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROLE_NOT_FOUND));

        user.getUserRoles().add(userRole);

        User savedUser = userRepository.save(user);

        // Генерация ролей в строку для токена
        String roles = savedUser.getUserRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.joining(","));

        // Генерация токенов
        Map<String, String> tokens = tokenManager.generateToken(savedUser.getEmail(), roles);

        // Создание ответа
        RegisterResponseDTO response = new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getSurname(),
                savedUser.getEmail(),
                tokens.get("accessToken"),
                tokens.get("refreshToken")
        );

        return BaseResponse.buildResponse(HttpStatus.CREATED, USER_REGISTERED_SUCCESSFULLY, response).toResponseEntity();
    }


    @Override
    public ResponseEntity<BaseResponse<LoginResponseDTO>> login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND));


//        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, PASSWORD_INCORRECT);
//        }

        String roles = user.getUserRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.joining(","));

        Map<String, String> tokens = tokenManager.generateToken(user.getEmail(), roles);

        LoginResponseDTO response = new LoginResponseDTO(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                tokens.get("accessToken"),
                tokens.get("refreshToken")
        );

        return BaseResponse.ok(response);
    }
}
