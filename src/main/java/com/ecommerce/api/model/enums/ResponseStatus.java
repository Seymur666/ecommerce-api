package com.ecommerce.api.model.enums;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    SUCCESS, FAILED, COMPLETED, PENDING, TIMEOUT, CANCELLED
}
