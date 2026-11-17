package com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces;

public interface DEKEncryption {

    // Generate a new DEK.
    DEKEncryption generate();

    // Reconstruct a DEK from existing key material.
    DEKEncryption reconstruct(byte[] keyMaterial);

    // Return the DEK's raw key material.
    byte[] getKeyMaterial();

    // Encrypt plaintext using this DEK.
    String encrypt(byte[] plaintext);

    // Decrypt an encrypted payload using this DEK.
    byte[] decrypt(String encryptedPayload);
}