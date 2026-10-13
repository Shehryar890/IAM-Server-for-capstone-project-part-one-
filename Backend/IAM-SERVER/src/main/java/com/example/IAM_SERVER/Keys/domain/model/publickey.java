package com.example.IAM_SERVER.Keys.domain.model;

import com.example.IAM_SERVER.Keys.domain.exceptions.publicKeyvalueException;

public class publickey {

    private  String value ;
    public  publickey(String value){
        if(value == null){
            throw new publicKeyvalueException("public key must not be null");
        }
       else  if(value.isEmpty()){
            throw new publicKeyvalueException("public key must not be empty");
        }
    else if(value.isBlank()) {
        throw new publicKeyvalueException("publickey shouldnt be empty");
    }
    else{
        this.value= value;

    }

    }
    String getvalue() {
        this.value = value;
    }
}
