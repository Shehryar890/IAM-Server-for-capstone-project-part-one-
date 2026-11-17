package com.example.IAM_SERVER.Keys.domain.exceptions;

public class InvalidJwtTokenException extends RuntimeException {

    public InvalidJwtTokenException(String message) {
        super(message);
    }

    public InvalidJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}