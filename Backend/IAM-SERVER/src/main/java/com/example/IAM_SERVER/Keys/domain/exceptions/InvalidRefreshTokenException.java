package com.example.IAM_SERVER.Keys.domain.exceptions;

public class InvalidRefreshTokenException
        extends RuntimeException {

    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}