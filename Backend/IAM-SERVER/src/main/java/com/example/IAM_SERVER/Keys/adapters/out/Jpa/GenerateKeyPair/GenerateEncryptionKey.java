package com.example.IAM_SERVER.Keys.adapters.out.Jpa.GenerateKeyPair;

import com.example.IAM_SERVER.Keys.domain.model.Algoritham;
import com.example.IAM_SERVER.Keys.domain.model.EncryptionKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.GenerateKeys.GenerateEncryptionKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.GenerateKeys.GenerateKeys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;

public class AesGenerateKey
        implements GenerateEncryptionKey {
    private Algoritham algoritham ;
public  Algoritham algoritham (Algoritham algoritham){
    this.algoritham = algoritham ;
}

    @Override
    public EncryptionKey generate(Void unused) {
        try {
            KeyGenerator generator =
                    KeyGenerator.getInstance(algoritham.name());

          SecretKey key =   generator.init(256);
          return key ;




        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(
                    "AES encryption key generation failed", e);
        }
    }
}
