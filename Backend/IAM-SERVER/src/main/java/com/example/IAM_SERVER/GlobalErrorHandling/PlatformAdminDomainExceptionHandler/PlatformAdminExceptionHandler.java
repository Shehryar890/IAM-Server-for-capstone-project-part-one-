package com.example.IAM_SERVER.GlobalErrorHandling.PlatformAdminDomainExceptionHandler;


import com.example.IAM_SERVER.GlobalErrorHandling.ErrorResponse;
import com.example.IAM_SERVER.platformAdmin.domain.exceptions.InvalidCredentialsException;
import com.example.IAM_SERVER.platformAdmin.domain.exceptions.TokenGenerationException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice(
        basePackages = "com.example.IAM_SERVER.platformAdmin.adapters.in.web.Controllers"
)
@Order(1)
public class PlatformAdminExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException exception,
            HttpServletRequest request
    ) {

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_CREDENTIALS",
                "Invalid username or password",
                request.getRequestURI(),
                generateTraceId(),
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<ErrorResponse> handleTokenGenerationFailure(
            TokenGenerationException exception,
            HttpServletRequest request
    ) {

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "TOKEN_GENERATION_FAILED",
                "Unable to complete authentication",
                request.getRequestURI(),
                generateTraceId(),
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}
