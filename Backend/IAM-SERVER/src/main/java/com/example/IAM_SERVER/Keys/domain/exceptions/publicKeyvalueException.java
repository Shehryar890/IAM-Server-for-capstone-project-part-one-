package com.example.IAM_SERVER.Keys.domain.exceptions;

public class publicKeyvalueException extends RuntimeException{
    public String message ;
      public publicKeyvalueException(String message){
          super (message);
      }
}
