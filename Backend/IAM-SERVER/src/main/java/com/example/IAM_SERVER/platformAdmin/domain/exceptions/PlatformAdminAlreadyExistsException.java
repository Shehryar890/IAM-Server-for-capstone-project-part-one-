package com.example.IAM_SERVER.platformAdmin.domain.exceptions;

public class PlatformAdminAlreadyExistsException extends RuntimeException{
    public  String  message;
    public PlatformAdminAlreadyExistsException(String message){
        super(message);
    }
}
