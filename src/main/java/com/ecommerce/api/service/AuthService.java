package com.ecommerce.api.service;

import com.ecommerce.api.dto.request.LoginRequestDTO;
import com.ecommerce.api.dto.request.RegisterRequestDTO;
import com.ecommerce.api.dto.response.LoginResponseDTO;
import com.ecommerce.api.dto.response.RegisterResponseDTO;
import com.ecommerce.api.model.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<BaseResponse<RegisterResponseDTO>> register(RegisterRequestDTO registerRequestDTO);

    ResponseEntity<BaseResponse<LoginResponseDTO>> login(LoginRequestDTO loginRequestDTO);
}
