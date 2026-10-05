package com.example.IAM_SERVER.Utility.HashPassword;

public interface HashPassword {
    String hashPassword(String password);
    boolean checkPassword (String Inputpassword , String hashpasswordstoredindatabase);
}
