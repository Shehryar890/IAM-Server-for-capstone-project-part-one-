
package com.example.IAM_SERVER.Keys.adapters.out.LoadKeyStoreFromfile;

import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeyStoreEnvProperties;
import com.example.IAM_SERVER.Keys.domain.ports.out.LoadKeyStore;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public final class FileLoadKeyStore implements LoadKeyStore {

    private final String keyStoreType;

    public FileLoadKeyStore(KeyStoreEnvProperties properties) {

        if (properties == null) {
            throw new IllegalArgumentException(
                    "Keystore properties must not be null."
            );
        }

        String type = properties.type();

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException(
                    "Keystore type must not be blank."
            );
        }

        type = type.trim();

        // Validate that Java supports this keystore type.
        try {
            KeyStore.getInstance(type);
        } catch (KeyStoreException exception) {
            throw new IllegalArgumentException(
                    "Unsupported keystore type: " + type,
                    exception
            );
        }

        this.keyStoreType = type;
    }

    @Override
    public KeyStore loadkeyStore(Path keystorePath, char[] password) {

        if (keystorePath == null || password == null) {
            throw new IllegalArgumentException(
                    "Keystore path and password must not be null."
            );
        }

        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);

            try (InputStream inputStream =
                         Files.newInputStream(keystorePath)) {
                keyStore.load(inputStream, password);
            }

            return keyStore;

        } catch (KeyStoreException
                 | IOException
                 | NoSuchAlgorithmException
                 | CertificateException exception) {

            throw new KeyStoreLoadingException(
                    "Unable to load the keystore.", exception
            );
        }
    }

    public static class KeyStoreLoadingException
            extends RuntimeException {

        public KeyStoreLoadingException(
                String message, Throwable cause) {
            super(message, cause);
        }
    }
}
