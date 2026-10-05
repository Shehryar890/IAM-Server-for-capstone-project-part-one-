package com.example.IAM_SERVER.platformAdmin.domain.model;

import com.example.IAM_SERVER.Utility.HashPassword.HashPassword;

import java.time.Instant;

public class platformAdmin {
    private int iamadmin_id  ;

    private String username;
    private PasswordHash password ;
   private Instant  createdAT ;
    private Instant updatedAt ;




    public String getUsername() {
        return this.username;
    }
//    public void setIamadmin_id(int id){
//        this.iamadmin_id = id;
//    }
//    int  getid(){
//        return  iamadmin_id;
//    }

    public void setUsername(String username){
        this.username  = username;
    }
public String  getpassword(){
      return   password.value();
}


public void setPassword(PasswordHash message){
        this.password = message;

}







}
