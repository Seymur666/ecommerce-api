package com.ecommerce.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {
    private HttpStatus status;
    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CommonResponse<T> error(HttpStatus status, String message) {
        return CommonResponse.<T>builder()
                .status(status)
                .code(status.value())
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}