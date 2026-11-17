package com.example.IAM_SERVER.Keys.domain.ports.out;

import javax.crypto.SecretKey;

public interface MasterEncryptionKeyManager {

    String encrypt(SecretKey key );

    SecretKey  decrypt(String encryptedPayload);
}