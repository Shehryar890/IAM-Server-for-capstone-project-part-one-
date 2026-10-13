package com.example.IAM_SERVER.platformAdmin.domain.model;

import com.example.IAM_SERVER.platformAdmin.domain.exceptions.InvalidPasswordException;

public class Adminpassword {
    private final String password ;

      public Adminpassword (String password){
          if(password.isEmpty() ){

              throw  new   InvalidPasswordException("password is empty");
          }
          if(password.matches("^[A-Za-z]{8,}$")){
              throw new InvalidPasswordException("password is week");
          }
          else {
              this.password = password;
          }






      }

   public  String  getpassword(){
        return  this.password;
    }




}
