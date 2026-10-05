package com.example.IAM_SERVER.platformAdmin.domain.model;

public class passwordchecekr {

        boolean passswordchekcer(int password) throws {
            if(password <8){
                throw new RuntimeException("password too short");
            }
            else{
                return true ;
            }
        }
}
