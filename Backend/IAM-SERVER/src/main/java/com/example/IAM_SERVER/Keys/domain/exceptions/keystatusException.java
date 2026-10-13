package com.example.IAM_SERVER.Keys.domain.exceptions;

public class keystatusException extends  RuntimeException {
    public String message ;

    public keystatusException(String message){
        super(message);
    }
}