package com.example.IAM_SERVER.Keys.adapters.out.GenerateKeyStore;


import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeyStoreEnvProperties;

import com.example.IAM_SERVER.Keys.adapters.out.GeneratePassword;
import com.example.IAM_SERVER.Keys.domain.ports.out.Keystorerecord;
import com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.GenerateKeyStore;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Base64;

public class PKCS12Keystore implements GenerateKeyStore {

    private final KeyStoreEnvProperties env;

    public  PKCS12Keystore(KeyStoreEnvProperties env){
        this.env = env;
    }



    @Override
    public Keystorerecord generate(Void unused) {
        byte[] passwordbytes  = GeneratePassword.generate();
        String password = Base64.getEncoder().encodeToString(passwordbytes);
        char [] passwordarray  = password.toCharArray();
try{
        KeyStore store = KeyStore.getInstance(env.type());
        store.load(null  , passwordarray);
        return new Keystorerecord(store , password );
}
catch (GeneralSecurityException e) {
    throw new IllegalStateException(
            "Failed to generate and initialize the keystore", e
    );
} catch (IOException e) {
    throw new RuntimeException(e);
} finally {
    Arrays.fill(passwordbytes , (byte)0);
    Arrays.fill(passwordarray, (char)0);



    }
    }
}

