package com.example.IAM_SERVER.GlobalErrorHandling;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(

        Instant timestamp,

        int status,

        String code,

        String message,

        String path,

        String traceId,

        List<FieldError> errors
) {

    public record FieldError(
            String field,
            String message
    ) {}
}