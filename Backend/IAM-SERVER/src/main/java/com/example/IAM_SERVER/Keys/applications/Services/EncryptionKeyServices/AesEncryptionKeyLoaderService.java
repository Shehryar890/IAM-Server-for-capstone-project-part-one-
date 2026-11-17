package com.example.IAM_SERVER.Keys.applications.Services.EncryptionKeyServices;

import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeyStoreEnvProperties;
import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeyStorePasswordProperties;

import com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair.AesGcmMasterEncryptionKeyManager;


import com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces.ReadPassword;

import com.example.IAM_SERVER.Keys.domain.ports.out.GetMasterEncryptionKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.LoadKeyStore;
import com.example.IAM_SERVER.Keys.domain.ports.out.MasterEncryptionKeyManager;
import com.example.IAM_SERVER.Utility.FileValidationUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Arrays;

@Service
public final class AesEncryptionKeyLoaderService {

    private final KeyStoreEnvProperties keyStoreProperties;
    private final KeyStorePasswordProperties passwordProperties;

    private final ReadPassword<char[], Path> passwordReader;
    private final LoadKeyStore keyStoreLoader;
    private final GetMasterEncryptionKey<SecretKey> getAesKey;

    public  AesEncryptionKeyLoaderService(
            KeyStoreEnvProperties keyStoreProperties,
            KeyStorePasswordProperties passwordProperties,
            ReadPassword<char[], Path> passwordReader,
            LoadKeyStore keyStoreLoader,
            GetMasterEncryptionKey<SecretKey> getAesKey) {

        this.keyStoreProperties = requireNonNull(
                keyStoreProperties, "Keystore properties");

        this.passwordProperties = requireNonNull(
                passwordProperties, "Password properties");

        this.passwordReader = requireNonNull(
                passwordReader, "Password reader");

        this.keyStoreLoader = requireNonNull(
                keyStoreLoader, "Keystore loader");

        this.getAesKey = requireNonNull(
                getAesKey, "AES key retriever");
    }

    public MasterEncryptionKeyManager loadEncryptionKey() {

        Path keyStorePath = keyStoreProperties.path();
        Path passwordPath = passwordProperties.path();

        char[] password = null;

        try {
            // 1. Validate both configured paths.
            FileValidationUtils.validateAbsolutePath(
                    keyStorePath, "Keystore path");

            FileValidationUtils.validateAbsolutePath(
                    passwordPath, "Password file path");

            // 2. Validate both files before reading anything.
            FileValidationUtils.validateReadableRegularFile(
                    keyStorePath, "Keystore file");

            FileValidationUtils.validateReadableRegularFile(
                    passwordPath, "Password file");

            // 3. Read the keystore password.
            password = passwordReader.readPassword(passwordPath);

            if (password == null || password.length == 0) {
                throw new EncryptionKeyLoadException(
                        "The keystore password is empty.");
            }

            // 4. Load the existing keystore.
            KeyStore keyStore =
                    keyStoreLoader.loadkeyStore(keyStorePath, password);

            if (keyStore == null) {
                throw new EncryptionKeyLoadException(
                        "The keystore loader returned null.");
            }

            // 5. Retrieve and validate the AES key.
            // GetAesKey performs algorithm-specific validation and
            // constructs the EncryptionKey domain object.
            SecretKey  encryptionKey = getAesKey.getkey(
                    keyStore,
                    "iam-encryption-key",
                    password
            );

            if (encryptionKey == null) {
                throw new EncryptionKeyLoadException(
                        "The AES key retriever returned null.");
            }
System.out.println("succesfully loaded the encryption key from the fileystem");

            return new AesGcmMasterEncryptionKeyManager(encryptionKey);

        } catch (EncryptionKeyLoadException exception) {
            throw exception;

        } catch (RuntimeException exception) {
            throw new EncryptionKeyLoadException(
                    "Unable to load the application's encryption key.",
                    exception
            );

        } finally {
            // 7. Clear the password on both success and failure.
            if (password != null) {
                Arrays.fill(password, '\0');
            }
        }
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(
                    name + " must not be null.");
        }

        return value;
    }

    public static final class EncryptionKeyLoadException
            extends RuntimeException {

        public EncryptionKeyLoadException(String message) {
            super(message);
        }

        public EncryptionKeyLoadException(
                String message, Throwable cause) {
            super(message, cause);
        }
    }
}
