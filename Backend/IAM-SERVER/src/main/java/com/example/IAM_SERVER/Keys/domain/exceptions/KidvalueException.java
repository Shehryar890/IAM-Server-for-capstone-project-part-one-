package com.example.IAM_SERVER.Keys.domain.exceptions;

public class KidvalueException extends  RuntimeException {

    private  String message;


    public KidvalueException(String message) {
        super(message);

    }

}
