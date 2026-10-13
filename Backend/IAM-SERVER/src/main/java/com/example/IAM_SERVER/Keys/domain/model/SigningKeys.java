package com.example.IAM_SERVER.Keys.domain.model;

import com.example.IAM_SERVER.Keys.domain.exceptions.KidvalueException;
import com.example.IAM_SERVER.Keys.domain.exceptions.keystatusException;
import com.example.IAM_SERVER.Keys.domain.exceptions.publicKeyvalueException;

import java.security.Key;
import java.time.Instant;

public class SigningKeys {

    private Long key_id;
    private Keystatus status;
    Kid kid;
    private publickey publickey;
    private Instant created_at;
    private Instant updated_at;
    private Instant expired_at;
    private Algoritham algoritham;
    private  Instant retire_at;

    public SigningKeys() {

    }


//
//    public void setCreated_at(Instant createdAt) {
//        this.created_at = createdAt ;
//    }
//
//    public void setUpdated_at(Instant updatedAt) {
//        this.updated_at = updatedAt;
//    }

   public  void setexpiredat(Instant expired_at) {

if(this.status == Keystatus.Retired){
    throw new keystatusException("key cant be expired");
}
this.expired_at = expired_at;
    }

  public   void activatekey (Keystatus status){
        if(status ==  Keystatus.Active){

            throw new keystatusException("key is already activated");
        }
        else if(status ==  Keystatus.Retired){
            this.status =  status;
        }

    }

    void retirekey(Keystatus status ){

        if(status  == Keystatus.Retired)
        {
            throw new keystatusException("kid is already retired");
    }
        else if (status == Keystatus.Active) {
            this.status = status ;
            this.retire_at = Instant.now();
        }
}

public void setAlgoritham(Algoritham algo){
        this.algoritham = algo;
}


public Keystatus getstatus(){
       return  this.status;
}
 public Instant getExpired_at(){
        return expired_at;
}

 public Algoritham  getalgoritham(){
        return this.algoritham;
}
public String getKid(){
        return this.kid.getValue();
}
public String getPublickey(){
        return this.publickey.getvalue();
}


    }







