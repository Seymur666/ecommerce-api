package com.ecommerce.api.controller;

import com.ecommerce.api.dto.request.LoginRequestDTO;
import com.ecommerce.api.dto.request.RegisterRequestDTO;
import com.ecommerce.api.dto.response.LoginResponseDTO;
import com.ecommerce.api.dto.response.RegisterResponseDTO;
import com.ecommerce.api.model.BaseResponse;
import com.ecommerce.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RegisterResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        return authService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }
}
