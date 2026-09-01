package com.chetan.productapi.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {

    private boolean success;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;
}
