package com.example.IAM_SERVER.platformAdmin.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public  String  message;
    public InvalidPasswordException(String message){
        super(message);
    }
}
