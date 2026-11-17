package com.example.IAM_SERVER.Keys.domain.ports.out;

import java.security.KeyStore;

public interface  GetMasterEncryptionKey <T>{

    T getkey(KeyStore k , String  alias , char[] password) ;
}
