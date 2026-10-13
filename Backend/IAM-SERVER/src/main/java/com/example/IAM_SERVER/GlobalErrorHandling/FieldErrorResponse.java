package com.example.IAM_SERVER.GlobalErrorHandling;

public record FieldErrorResponse(
        String field,
        String code,
        String message
) {
}
