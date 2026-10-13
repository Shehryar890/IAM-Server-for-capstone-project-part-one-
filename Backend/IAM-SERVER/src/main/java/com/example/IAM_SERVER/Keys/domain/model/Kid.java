package com.example.IAM_SERVER.Keys.domain.model;

import com.example.IAM_SERVER.Keys.domain.exceptions.KidvalueException;
import com.example.IAM_SERVER.Keys.domain.exceptions.publicKeyvalueException;

public class Kid {
   private  String value ;

    public Kid(String incomingvalue){

        if(incomingvalue == null)
        {
            throw new publicKeyvalueException("publickey must not be null");

        }
        if(incomingvalue.isEmpty()){
            throw new KidvalueException("key kid value should not be empty");
        }

        if(incomingvalue.isBlank()){
            throw new KidvalueException("kid must contain letters ");

        }        else{
            this.value = incomingvalue;

        }



    }


   public  String  getValue( ){
        return this.value;
    }


}
