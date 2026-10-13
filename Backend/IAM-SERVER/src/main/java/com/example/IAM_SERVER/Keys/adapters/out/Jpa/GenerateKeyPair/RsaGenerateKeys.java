package com.example.IAM_SERVER.Keys.adapters.out.Jpa.GenerateKeyPair;




import com.example.IAM_SERVER.Keys.domain.model.Algoritham;
import com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.GenerateKeys.GenerateKeys;
import jakarta.validation.constraints.Null;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RsaGenerateKeys implements GenerateKeys {

    private final  Algoritham algoritham;

    public RsaGenerateKeys(Algoritham algoritham){
        this.algoritham = algoritham;
    }



    @Override
    public KeyPair generate(Void unused) {
        try {
            KeyPairGenerator generator =
                    KeyPairGenerator.getInstance(algoritham.name());

            generator.initialize(3072);

            return generator.generateKeyPair();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "RSA key generation failed", e);
        }
    }
}

