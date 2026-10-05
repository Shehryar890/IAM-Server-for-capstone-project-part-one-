package com.example.IAM_SERVER.platformAdmin.domain.model;

public class platformAdmin {
    private int iamadmin_id  ;

    private String username;
    private  String  password ;
   private  String createdAT ;
    private String updatedAt ;


    public String getPassword(){
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username){
        this.username  = username;
    }
    public void setPassword(String password){
        this.password = password;

    }







}
