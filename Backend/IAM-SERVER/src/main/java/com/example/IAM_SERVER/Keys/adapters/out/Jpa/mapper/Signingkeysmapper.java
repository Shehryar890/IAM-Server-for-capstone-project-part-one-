package com.example.IAM_SERVER.Keys.adapters.out.Jpa.mapper;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.SigningkeyEntity;
import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;

public class Signingkeysmapper {


    public  static SigningkeyEntity getkeyentity(SigningKeys object){
        return new SigningkeyEntity(
                object.getKid(),
                object.getalgoritham().name(),
                object.getPublickey(),
                object.getstatus()
        );



    }
}
