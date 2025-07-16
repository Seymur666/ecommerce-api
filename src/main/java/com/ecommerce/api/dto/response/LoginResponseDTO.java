package com.ecommerce.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    String name;
    String surname;
    String email;
    String accessToken;
    String refreshToken;
}
