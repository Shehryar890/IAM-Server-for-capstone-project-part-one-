package com.example.IAM_SERVER.Keys.domain.ports.out;

import javax.crypto.SecretKey;
import java.security.PrivateKey;

public interface DekKeyEncryptor {




        String encrypt(SecretKey dek, PrivateKey privateKey);

        PrivateKey decrypt(SecretKey dek, String encryptedPayload);
    }
