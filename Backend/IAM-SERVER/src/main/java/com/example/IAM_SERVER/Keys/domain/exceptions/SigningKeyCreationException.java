package com.example.IAM_SERVER.Keys.domain.exceptions;

public final class SigningKeyCreationException extends RuntimeException {

    public SigningKeyCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}