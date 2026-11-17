package com.example.IAM_SERVER.platformAdmin.domain.exceptions;

public class TokenGenerationException extends RuntimeException{
    public TokenGenerationException (String message){
        super(message);
    }
}
