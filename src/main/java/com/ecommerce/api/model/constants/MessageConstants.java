package com.ecommerce.api.model.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageConstants {
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String PASSWORD_INCORRECT = "Password is incorrect";
    public static final String ROLE_NOT_FOUND = "Default role not found";
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
}