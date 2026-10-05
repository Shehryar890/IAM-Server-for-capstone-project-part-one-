package com.example.IAM_SERVER.Utility.HashPassword;

import com.example.IAM_SERVER.Config.Bcrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptpasswordHasher  implements  HashPassword{
BCryptPasswordEncoder encoder ;
public BcryptpasswordHasher(BCryptPasswordEncoder encoder){
    this.encoder = encoder;
}
    @Override
    public String hashPassword(String password) {

            return encoder.encode(password);


    }

    @Override
    public boolean checkPassword(String Inputpassword, String hashpasswordstoredindatabase) {
        return encoder.matches(Inputpassword , hashpasswordstoredindatabase);
    }

}
