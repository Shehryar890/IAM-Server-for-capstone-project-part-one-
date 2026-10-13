package com.example.IAM_SERVER.GlobalErrorHandling;



import com.example.IAM_SERVER.GlobalErrorHandling.FieldErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URI;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        URI type,
        String title,
        int status,
        String detail,
        URI instance,
        String code,
        String traceId,
        List<FieldErrorResponse> errors
) {
    public ApiErrorResponse {
        errors = errors == null
                ? null
                : List.copyOf(errors);
    }
}