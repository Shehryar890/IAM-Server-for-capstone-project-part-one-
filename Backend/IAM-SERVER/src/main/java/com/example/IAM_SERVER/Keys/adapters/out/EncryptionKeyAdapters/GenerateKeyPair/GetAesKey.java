package com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair;



import javax.crypto.SecretKey;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.util.Arrays;

public final class GetAesKey implements com.example.IAM_SERVER.Keys.domain.ports.out.GetMasterEncryptionKey<SecretKey> {

    @Override
    public SecretKey getkey(
            KeyStore keyStore,
            String alias,
            char[] password) {

        if (keyStore == null || alias == null || alias.isBlank()
                || password == null || password.length == 0) {
            throw new IllegalArgumentException(
                    "Keystore, alias, and password must be valid."
            );
        }

        try {
            KeyStore.Entry entry = keyStore.getEntry(
                    alias,
                    new KeyStore.PasswordProtection(password)
            );

            if (!(entry instanceof KeyStore.SecretKeyEntry secretEntry)) {
                throw new AesKeyLoadingException(
                        "The alias does not contain a secret-key entry."
                );
            }

            SecretKey secretKey = secretEntry.getSecretKey();

            if (secretKey == null
                    || !"AES".equalsIgnoreCase(secretKey.getAlgorithm())) {
                throw new AesKeyLoadingException(
                        "The stored key is not an AES key."
                );
            }

            byte[] encoded = secretKey.getEncoded();

            if (encoded == null) {
                throw new AesKeyLoadingException(
                        "The AES key encoding is unavailable for validation."
                );
            }

            try {
                if (encoded.length != 32) {
                    throw new AesKeyLoadingException(
                            "The AES key must be 256 bits."
                    );
                }
            } finally {
                Arrays.fill(encoded, (byte) 0);
            }

            // Return the actual SecretKey.
            return secretKey;

        } catch (KeyStoreException
                 | NoSuchAlgorithmException
                 | UnrecoverableEntryException exception) {

            throw new AesKeyLoadingException(
                    "Failed to retrieve the AES key.", exception
            );
        }
    }

    public static final class AesKeyLoadingException
            extends RuntimeException {

        public AesKeyLoadingException(String message) {
            super(message);
        }

        public AesKeyLoadingException(
                String message, Throwable cause) {
            super(message, cause);
        }
    }
}