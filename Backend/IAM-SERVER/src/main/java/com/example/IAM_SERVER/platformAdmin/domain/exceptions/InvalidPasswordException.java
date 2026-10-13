package com.example.IAM_SERVER.platformAdmin.domain.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public  String  message;
    public InvalidPasswordException(String message){
        super(message);
    }
}
