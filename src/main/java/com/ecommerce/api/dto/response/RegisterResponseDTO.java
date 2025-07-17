package com.ecommerce.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDTO {
    Long id;
    String name;
    String surname;
    String email;
}