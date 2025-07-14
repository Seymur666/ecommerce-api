package com.ecommerce.api.service;

import com.ecommerce.api.dto.request.LoginRequestDTO;
import com.ecommerce.api.dto.request.RegisterRequestDTO;
import com.ecommerce.api.dto.response.ResponseDTO;

public interface AuthService {
    ResponseDTO register(RegisterRequestDTO registerRequestDTO);

    ResponseDTO login(LoginRequestDTO loginRequestDTO);
}
