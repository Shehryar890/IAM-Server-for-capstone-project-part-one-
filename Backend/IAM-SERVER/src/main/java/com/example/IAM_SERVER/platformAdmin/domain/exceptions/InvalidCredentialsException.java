package com.example.IAM_SERVER.platformAdmin.domain.exceptions;

public class InvalidCredentialsException extends  RuntimeException{
    public InvalidCredentialsException(String message){
        super(message);
    }
}
