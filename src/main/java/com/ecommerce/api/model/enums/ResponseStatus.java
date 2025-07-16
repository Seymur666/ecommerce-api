package com.ecommerce.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESS("Operation completed successfully"),
    FAILED("Operation failed"),
    COMPLETED("Operation completed"),
    PENDING("Operation pending"),
    TIMEOUT("Operation timeout"),
    CANCELLED("Operation cancelled");

    private final String description;
}
